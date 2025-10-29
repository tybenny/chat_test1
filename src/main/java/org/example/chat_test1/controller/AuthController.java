package org.example.chat_test1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.chat_test1.dto.AuthResponse;
import org.example.chat_test1.dto.LoginRequest;
import org.example.chat_test1.entity.User;
import org.example.chat_test1.dto.RegisterRequest;
import org.example.chat_test1.service.UserService;
import org.example.chat_test1.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);

            //tao token sau khi dang ki
            String token = jwtUtil.generateToken(request.getUsername());

            AuthResponse response = new AuthResponse(
                    token,
                    user.getUsername(),
                    user.getEmail(),
                    user.getId()
            );

            return ResponseEntity.ok("Dang ky thanh cong: " + response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // 1. Goi service de verify username + password
            User user = userService.login(request);

            // 2. Tao JWT token
            String token = jwtUtil.generateToken(request.getUsername());

            //3. Tra ve response chua thong tin token + thong tin user
            AuthResponse response = new AuthResponse(
                    token,
                    user.getUsername(),
                    user.getEmail(),
                    user.getId()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
