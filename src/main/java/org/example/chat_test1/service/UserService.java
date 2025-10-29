package org.example.chat_test1.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.chat_test1.dto.*;
import org.example.chat_test1.entity.User;
import org.example.chat_test1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //method dang ki
    public User register(RegisterRequest request) {
        //kiem tra username da ton tai
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username da ton tai");
        }

        //kiem tra email da ton tai
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email da ton tai");
        }

        //tao user moi
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    //method dang nhap
    public User login(LoginRequest request) {
        // 1. tim user theo username
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (!userOptional.isPresent()) {
            throw new RuntimeException("Sai username hoac password");
        }

        User user = userOptional.get();

        // 2. Kiem tra password co khop khong
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(), //password user nhap
                user.getPassword() //password hash trong DB
        );

        if (!passwordMatches) {
            throw new RuntimeException("Sai username hoac password");
        }

        // 3. Dang nhap thanh cong
        return user;
    }

    //method tim theo username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }
}
