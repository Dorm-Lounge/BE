package dorm.lounge.domain.auth.service;

import dorm.lounge.domain.auth.converter.AuthConverter;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;
import dorm.lounge.domain.auth.dto.AuthProfile;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import dorm.lounge.global.security.JwtProvider;
import dorm.lounge.global.security.OAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final OAuthUtil oAuthUtil;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final NicknameGenerator nicknameGenerator;

    @Override
    public AuthUserResponse kakaoLoginWithAccessToken(String accessToken) {
        // 1. 카카오 사용자 프로필 조회
        AuthProfile profile = oAuthUtil.getKakaoProfile(accessToken);

        String nickname = nicknameGenerator.generate(); // 랜덤 닉네임

        // 2. 기존 사용자 조회 or 신규 등록
        User user = userRepository.findByEmail(profile.getEmail()).orElseGet(() ->
                userRepository.save(User.builder()
                        .email(profile.getEmail())
                        .name(profile.getName())
                        .nickname(nickname)
                        .profileImage(profile.getProfileImage())
                        .gpsVerified(false) // 첫 가입자만 false 설정
                        .build())
        );

        // 3. JWT 발급
        String token = jwtProvider.createAccessToken(user.getUserId(), user.getEmail());

        // 4. 응답 DTO 반환
        return AuthConverter.toAuthUserResponse(user, token);
    }
}