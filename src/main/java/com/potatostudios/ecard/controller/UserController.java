package com.potatostudios.ecard.controller;

import com.potatostudios.ecard.model.User;
import com.potatostudios.ecard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        Optional<User> user = userService.getUserByEmail(loginUser.getEmail());

        if (user.isPresent() && userService.checkPassword(loginUser.getPassword(), user.get().getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}