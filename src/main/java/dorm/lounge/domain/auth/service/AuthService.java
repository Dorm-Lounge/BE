package dorm.lounge.domain.auth.service;

import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;

public interface AuthService {
    AuthUserResponse kakaoLoginWithAccessToken(String accessToken);
}
