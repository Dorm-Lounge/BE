package dorm.lounge.global.security;

import dorm.lounge.domain.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKeyString;

    private static final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 1000L; // 1시간
    private static final long REFRESH_TOKEN_VALID_TIME = 604800000; // 7일
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId, String email) {
        return createToken(userId, email, ACCESS_TOKEN_VALID_TIME);
    }

    public String createRefreshToken(Long userId, String email)  {
        return createToken(userId, email, REFRESH_TOKEN_VALID_TIME);
    }

    private String createToken(Long userId, String email, long expirationMs) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // userId를 Subject로 저장
                .claim("email", email) // email을 claims로 저장
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
