// ErrorStatus.java
package dorm.lounge.global.status;

import dorm.lounge.global.BaseErrorCode;
import dorm.lounge.global.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // Common
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 에러입니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),

    // Auth
    AUTH_USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH401", "사용자를 찾을 수 없습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH402", "유효하지 않은 토큰입니다."),
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH403", "인증되지 않은 사용자입니다."),
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH403", "접근 권한이 없습니다."),
    AUTH_REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "AUTH_404", "RefreshToken 정보가 일치하지 않습니다."),
    AUTH_REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH_405", "RefreshToken이 유효하지 않습니다."),
    AUTH_GPS_UNVERIFIED(HttpStatus.FORBIDDEN, "AUTH406", "GPS 인증이 필요합니다."),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404", "게시글을 찾을 수 없습니다."),
    POST_ACCESS_DENIED(HttpStatus.FORBIDDEN, "POST403", "해당 게시글에 대한 권한이 없습니다."),
    POST_ALREADY_LIKED(HttpStatus.CONFLICT, "POST404", "이미 좋아요를 누른 게시글입니다."),
    POST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "POST405", "좋아요한 게시글이 없습니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404", "댓글을 찾을 수 없습니다."),
    COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "COMMENT403", "해당 댓글에 대한 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(false)
                .code(this.code)
                .message(this.message)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return getReason();
    }
}
