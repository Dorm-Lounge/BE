package dorm.lounge.domain.user.service;

import dorm.lounge.domain.user.converter.UserConverter;
import dorm.lounge.domain.user.dto.UserDTO.UserRequest.GpsRequest;
import dorm.lounge.domain.user.dto.UserDTO.UserResponse.MyPageResponse;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void verifyGps(String userId, GpsRequest request) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        if (request.isSuccess()) {
            user.updateGps(LocalDateTime.now());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MyPageResponse getMyPage(String userId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        return UserConverter.toMyPageResponse(user);
    }
}
