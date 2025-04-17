package dorm.lounge.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class AuthDTO {
    public static class AuthRequest {

        @Getter
        @NoArgsConstructor
        public static class SocialLoginRequest {
            private String accessToken;
        }

        @Getter
        @NoArgsConstructor
        public static class TokenRefreshRequest {
            private String refreshToken;
        }
    }

    public static class AuthResponse {

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class AuthUserResponse {
            private String email;
            private String name;
            private String nickname;
            private String profileImage;
            private String accessToken;
            private String refreshToken;
            private Boolean gpsVerified;
            private LocalDateTime gpsVerifiedAt;
            private Boolean requireGps; // GPS 인증 필요 여부
        }

        @Getter
        @AllArgsConstructor
        @Builder
        public static class TokenRefreshResponse {
            private String accessToken;
            private String refreshToken;
        }
    }
}
