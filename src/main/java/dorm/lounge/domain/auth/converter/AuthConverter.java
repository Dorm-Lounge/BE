package dorm.lounge.domain.auth.converter;

import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;
import dorm.lounge.domain.user.entity.User;

public class AuthConverter {
    public static AuthUserResponse toAuthUserResponse(Boolean requireGps, User user, String accessToken, String refreshToken) {
        return AuthUserResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .gpsVerified(user.getGpsVerified())
                .gpsVerifiedAt(user.getGpsVerifiedAt())
                .email(user.getEmail())
                .name(user.getNickname())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .requireGps(requireGps)
                .build();
    }
}
