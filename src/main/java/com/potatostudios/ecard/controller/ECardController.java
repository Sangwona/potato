package com.potatostudios.ecard.controller;

import com.potatostudios.ecard.model.ECard;
import com.potatostudios.ecard.repository.ECardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:8080") // 프론트엔드 요청 허용
@RestController
@RequestMapping("/ecard")
public class ECardController {
    private final ECardRepository eCardRepository;

    public ECardController(ECardRepository eCardRepository) {
        this.eCardRepository = eCardRepository;
    }

    // e-card 생성
    @PostMapping
    public ResponseEntity<ECard> createECard(@RequestBody ECard eCard) {
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
