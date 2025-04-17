package dorm.lounge.domain.auth.service;

import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthUserResponse kakaoLoginWithAccessToken(String accessToken);
}
