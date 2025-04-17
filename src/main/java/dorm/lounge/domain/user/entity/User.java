package dorm.lounge.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String nickname;

    private String email;

    private String profileImage;

    private Boolean gpsVerified;

    private LocalDateTime gpsVerifiedAt;

    @Enumerated(EnumType.STRING)
    private Building building;

    private String roomNumber;

    public void updateGps(LocalDateTime verifiedAt) {
        this.gpsVerified = true;
        this.gpsVerifiedAt = verifiedAt;
    }

    public void clearGps() {
        this.gpsVerified = false;
        this.gpsVerifiedAt = null;
    }
}
