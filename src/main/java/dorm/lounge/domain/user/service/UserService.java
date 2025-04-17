package dorm.lounge.domain.user.service;

import dorm.lounge.domain.user.dto.UserDTO.UserRequest.GpsRequest;
import dorm.lounge.domain.user.dto.UserDTO.UserResponse.MyPageResponse;

public interface UserService {
    void verifyGps(String userId, GpsRequest request);
    MyPageResponse getMyPage(String userId);
}
