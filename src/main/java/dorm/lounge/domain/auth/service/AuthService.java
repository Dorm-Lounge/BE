package dorm.lounge.domain.auth.service;

import dorm.lounge.domain.auth.dto.AuthDTO.AuthRequest.SocialLoginRequest;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;

import dorm.lounge.domain.auth.dto.AuthDTO.AuthRequest.TokenRefreshRequest;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.TokenRefreshResponse;

public interface AuthService {
    AuthUserResponse kakaoLoginWithAccessToken(SocialLoginRequest socialLoginRequest);
    TokenRefreshResponse refreshAccessToken(TokenRefreshRequest tokenRefreshRequest);
}
