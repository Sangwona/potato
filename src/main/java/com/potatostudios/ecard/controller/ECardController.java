package com.potatostudios.ecard.controller;

import com.potatostudios.ecard.model.ECard;
import com.potatostudios.ecard.model.User;
import com.potatostudios.ecard.repository.ECardRepository;
import com.potatostudios.ecard.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:8080") // 프론트엔드 요청 허용
@RestController
@RequestMapping("/ecard")
public class ECardController {
    private final ECardRepository eCardRepository;
    private final UserRepository userRepository;

    public ECardController(ECardRepository eCardRepository, UserRepository userRepository) {
        this.eCardRepository = eCardRepository;
        this.userRepository = userRepository;
    }


    // e-card 생성
    @PostMapping
    public ResponseEntity<?> createECard(@RequestBody Map<String, String> requestData) {
        String title = requestData.get("title");
        String message = requestData.get("message");
        String sender = requestData.get("sender");
        String recipient = requestData.get("recipient");
        UUID userId = UUID.fromString(requestData.get("userId")); // userId를 받아서 User와 연결

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        ECard eCard = new ECard(title, message, sender, recipient, user);
        ECard savedECard = eCardRepository.save(eCard);
        return ResponseEntity.ok(savedECard);
    }

    // 특정 e-card 조회
    @GetMapping("{id}")
    public ResponseEntity<?> getECard(@PathVariable UUID id) {
        Optional<ECard> eCard = eCardRepository.findById(id);
        return eCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 모든 e-card 조회
    @GetMapping
    public List<ECard> getAllECards() {
        return eCardRepository.findAll();
    }
}
