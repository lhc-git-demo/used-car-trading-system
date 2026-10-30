package com.usedcar.system.controller;

import com.usedcar.system.dto.AuthResponse;
import com.usedcar.system.dto.LoginRequest;
import com.usedcar.system.dto.RegisterRequest;
import com.usedcar.system.entity.User;
import com.usedcar.system.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "用户名不能为空"));
        }
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "用户名已存在"));
        }

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname() == null || request.nickname().isBlank() ? request.username() : request.nickname())
                .phone(request.phone())
                .email(request.email())
                .role("USER")
                .enabled(true)
                .build();

        User saved = userRepository.save(user);
        return ResponseEntity.ok(new AuthResponse(saved.getId(), saved.getUsername(), saved.getNickname(), saved.getRole(), "demo-token"));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.username()).orElse(null);
        if (user == null || !passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "用户名或密码错误"));
        }

        return ResponseEntity.ok(new AuthResponse(user.getId(), user.getUsername(), user.getNickname(), user.getRole(), "demo-token"));
    }
}
