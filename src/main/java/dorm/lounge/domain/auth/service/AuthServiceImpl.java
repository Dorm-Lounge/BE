package dorm.lounge.domain.auth.service;

import dorm.lounge.domain.auth.converter.AuthConverter;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;
import dorm.lounge.domain.auth.dto.AuthProfile;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import dorm.lounge.global.security.JwtProvider;
import dorm.lounge.global.security.JwtUtil;
import dorm.lounge.global.security.OAuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final OAuthUtil oAuthUtil;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final NicknameGenerator nicknameGenerator;

    @Override
    @Transactional
    public AuthUserResponse kakaoLoginWithAccessToken(String kakaoAccessToken) {
        // 1. 카카오 사용자 프로필 조회
        AuthProfile profile = oAuthUtil.getKakaoProfile(kakaoAccessToken);

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
}