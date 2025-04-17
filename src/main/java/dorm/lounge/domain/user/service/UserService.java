package dorm.lounge.domain.user.service;

import dorm.lounge.domain.user.dto.UserDTO.UserRequest.GpsRequest;
import dorm.lounge.domain.user.dto.UserDTO.UserResponse.GpsResponse;

public interface UserService {
    GpsResponse verifyGps(String userId, GpsRequest request);
}
