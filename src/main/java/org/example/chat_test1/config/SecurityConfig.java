package org.example.chat_test1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(                   //Nhung trang nay duoc quyen truy cap
                                                            // ma khong can dang nhap
                                "/api/auth/**",
                                "/",                      // Trang chủ
                                "/chat.html",             // Trang chat
                                "/css/**",                // File CSS
                                "/js/**",                 // File JavaScript
                                "/webjars/**",            // Thư viện web
                                "/ws/**"                  // WebSocket
                        ).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
