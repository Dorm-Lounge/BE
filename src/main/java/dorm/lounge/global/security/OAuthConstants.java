package dorm.lounge.global.security;

public class OAuthConstants {

    // Token 요청 URL
    public static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    // Profile 요청 URL
    public static final String KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";

    private OAuthConstants() {
        // 상수 클래스는 인스턴스화 금지
    }
}
