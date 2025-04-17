package dorm.lounge.global.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dorm.lounge.domain.auth.dto.AuthProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OAuthUtil {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public AuthProfile getKakaoProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                OAuthConstants.KAKAO_PROFILE_URL,
                HttpMethod.GET,
                request,
                String.class
        );

        try {
            JsonNode json = objectMapper.readTree(response.getBody());
            JsonNode account = json.get("kakao_account");
            JsonNode profile = account.get("profile");

            return AuthProfile.builder()
                    .email(account.get("email").asText())
                    .name(profile.get("nickname").asText())
                    .profileImage(profile.get("profile_image_url").asText())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("카카오 프로필 파싱 실패");
        }
    }
}