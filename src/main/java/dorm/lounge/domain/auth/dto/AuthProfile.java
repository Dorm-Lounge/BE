package dorm.lounge.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class AuthProfile {
    private String email;
    private String name;
    private String profileImage;
}
