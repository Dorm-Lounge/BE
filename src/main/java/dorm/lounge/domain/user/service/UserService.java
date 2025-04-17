package dorm.lounge.domain.user.service;

import dorm.lounge.domain.user.dto.UserDTO.UserRequest.GpsRequest;

public interface UserService {
    void verifyGps(String userId, GpsRequest request);
}
