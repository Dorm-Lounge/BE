package dorm.lounge.domain.auth.service;

import dorm.lounge.domain.auth.converter.AuthConverter;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.TokenRefreshResponse;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthRequest.TokenRefreshRequest;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthRequest.SocialLoginRequest;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;
import dorm.lounge.domain.auth.dto.AuthProfile;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import dorm.lounge.global.security.JwtProvider;
import dorm.lounge.global.security.JwtUtil;
import dorm.lounge.global.security.OAuthUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final OAuthUtil oAuthUtil;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final NicknameGenerator nicknameGenerator;

    @Override
    @Transactional
    public AuthUserResponse kakaoLoginWithAccessToken(SocialLoginRequest socialLoginRequest) {
        // 1. 카카오 사용자 프로필 조회
        AuthProfile profile = oAuthUtil.getKakaoProfile(socialLoginRequest.getAccessToken());

        String nickname = nicknameGenerator.generate(); // 랜덤 닉네임

        // 2. 기존 사용자 조회 or 신규 등록
        User user = userRepository.findByEmail(profile.getEmail()).orElse(null);

        // GPS 인증 필요 여부
        boolean requireGps;

        if (user == null) {
            // 신규 가입자
            user = userRepository.save(User.builder()
                    .email(profile.getEmail())
                    .name(profile.getName())
                    .nickname(nickname)
                    .profileImage(profile.getProfileImage())
                    .gpsVerified(false)
                    .build());
            requireGps = true;
        }  else {
            // GPS 인증 유효성 확인 후 필요 시 인증 상태 초기화
            requireGps = isGpsExpiredOrNotVerified(user);
            if (requireGps) {
                user.clearGps(); // 인증 상태 초기화
            }
        }

        // 3. JWT 발급
        String accessToken = jwtProvider.createAccessToken(user.getUserId(), user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId(), user.getEmail());

        user.updateRefreshToken(refreshToken);

        // 4. 응답 DTO 반환
        return AuthConverter.toAuthUserResponse(requireGps, user, accessToken, refreshToken);
    }

    public boolean isGpsExpiredOrNotVerified(User user) {
        if (user.getGpsVerified() == null || !user.getGpsVerified()) {
            return true;
        }
        if (user.getGpsVerifiedAt() == null) return true;
        return user.getGpsVerifiedAt().plusMonths(3).isBefore(java.time.LocalDateTime.now());
    }

    @Override
    @Transactional
    public TokenRefreshResponse refreshAccessToken(TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();

        // 1. 토큰 유효성 검증
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 RefreshToken입니다.");
        }

        // 2. 사용자 정보 추출
        Claims claims = jwtUtil.parseClaims(refreshToken);
        Long userId = jwtUtil.extractUserId(claims);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 3. 사용자 DB의 refreshToken과 비교
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("RefreshToken 불일치");
        }

        // 4. 새로운 AccessToken 발급
        String newAccessToken = jwtProvider.createAccessToken(user.getUserId(), user.getEmail());
        String newRefreshToken = jwtProvider.createRefreshToken(user.getUserId(), user.getEmail());

        user.updateRefreshToken(newRefreshToken);

        return AuthConverter.toTokenRefreshResponse(newAccessToken, newRefreshToken);

    }
}