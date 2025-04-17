package dorm.lounge.domain.user.converter;

import dorm.lounge.domain.user.dto.UserDTO.UserResponse.MyPageResponse;
import dorm.lounge.domain.user.entity.User;

public class UserConverter {
    public static MyPageResponse toMyPageResponse(User user) {
        return MyPageResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .gpsVerified(user.getGpsVerified())
                .gpsVerifiedAt(user.getGpsVerifiedAt())
                .build();
    }
}
