package dorm.lounge.domain.auth.controller;

import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthRequest.SocialLoginRequest;
import dorm.lounge.domain.auth.service.AuthService;
import dorm.lounge.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth 컨트롤러", description = "카카오 로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao/login")
    @Operation(summary = "카카오 로그인 API", description = "카카오 access token 기반 로그인 API")
    public ApiResponse<AuthUserResponse> kakaoLogin(@RequestBody SocialLoginRequest request) {
        return ApiResponse.onSuccess(authService.kakaoLoginWithAccessToken(request.getAccessToken()));
    }
}
