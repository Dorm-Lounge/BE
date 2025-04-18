package dorm.lounge.domain.user.controller;

import dorm.lounge.domain.user.dto.UserDTO.UserResponse.MyPageResponse;
import dorm.lounge.domain.user.dto.UserDTO.UserRequest.GpsRequest;
import dorm.lounge.domain.user.service.UserService;
import dorm.lounge.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User 컨트롤러", description = "User 관련 API")
public class UserController {

    private final UserService userService;
    @PostMapping("/gps")
    @Operation(summary = "GPS 인증 처리", description = "클라이언트 측 인증 결과에 따라 인증 상태 저장")
    public ApiResponse<String> verifyGps(Authentication authentication, @RequestBody GpsRequest request) {
        userService.verifyGps(authentication.getName(), request);
        return ApiResponse.onSuccess("인증 성공");
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 조회", description = "로그인한 사용자의 마이페이지 정보를 조회합니다.")
    public ApiResponse<MyPageResponse> getMyPage(Authentication authentication) {
        MyPageResponse response = userService.getMyPage(authentication.getName());
        return ApiResponse.onSuccess(response);
    }

}
