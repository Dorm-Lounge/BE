package dorm.lounge.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDTO {
    public static class UserRequest {
        @Getter
        @NoArgsConstructor
        public static class GpsRequest {
            private boolean success; // 클라이언트에서 GPS 인증 성공 여부
        }
    }

    public static class UserResponse {

    }
}
