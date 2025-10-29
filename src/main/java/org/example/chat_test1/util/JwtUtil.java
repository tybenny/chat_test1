package org.example.chat_test1.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    // SECRET KEY - Khóa bí mật để ký token (như chữ ký của bạn)
    // Trong thực tế nên để trong application.properties
    private static final String SECRET_KEY = "mySecretKeyForJWTTokenGenerationAndValidation12345678901234567890";

    //token co hieu luc trong 24 gio
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    //lay key de ky token
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    //tao token tu username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    //tao token voi thong tin cu the
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date exprirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims) //thong tin bo sung neu co
                .setSubject(subject) //Username
                .setIssuedAt(now) //thoi gian tao
                .setExpiration(exprirationDate) //thoi gian het han token
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) //ky token
                .compact();
    }

    //Giai ma token - lay thong tin tu token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //lay username tu token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    //lay thoi gian het han token
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    //kiem tra token da het han chua
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //validate token - kiem tra xem token co hop le khong
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
