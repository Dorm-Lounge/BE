package dorm.lounge.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserDTO {
    public static class UserRequest {
        @Getter
        @NoArgsConstructor
        public static class GpsRequest {
            private boolean success; // 클라이언트에서 GPS 인증 성공 여부
        }
    }

    public static class UserResponse {
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class MyPageResponse {
            private String name;
            private String email;
            private String nickname;
            private String profileImage;
            private Boolean gpsVerified;
            private LocalDateTime gpsVerifiedAt;
        }
    }
}
