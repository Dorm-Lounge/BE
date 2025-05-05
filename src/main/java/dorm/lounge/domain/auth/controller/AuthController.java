package dorm.lounge.domain.auth.controller;

import dorm.lounge.domain.auth.dto.AuthDTO.AuthRequest.TokenRefreshRequest;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.TokenRefreshResponse;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthResponse.AuthUserResponse;
import dorm.lounge.domain.auth.dto.AuthDTO.AuthRequest.SocialLoginRequest;
import dorm.lounge.domain.auth.service.AuthService;
import dorm.lounge.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth 컨트롤러", description = "카카오 로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao/login")
    @Operation(summary = "카카오 로그인 API", description = "카카오 access token 기반 로그인 API")
    public ApiResponse<AuthUserResponse> kakaoLogin(@RequestBody SocialLoginRequest request) {
        return ApiResponse.onSuccess(authService.kakaoLoginWithAccessToken(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "AccessToken 재발급", description = "RefreshToken을 통해 새로운 AccessToken을 재발급")
    public ApiResponse<TokenRefreshResponse> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        return ApiResponse.onSuccess(authService.refreshAccessToken(request));
    }

    @DeleteMapping("/logout")
    @Operation(summary = "회원 탈퇴", description = "현재 로그인한 사용자가 회원 탈퇴를 수행합니다.")
    public ApiResponse<String> withdraw(Authentication authentication) {
        authService.withdraw(authentication.getName());
        return ApiResponse.onSuccess("회원 탈퇴가 완료되었습니다.");
    }

}
