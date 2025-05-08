package dorm.lounge.global.exception;

import dorm.lounge.global.ApiResponse;
import dorm.lounge.global.BaseErrorCode;
import dorm.lounge.global.ErrorReasonDTO;
import dorm.lounge.global.status.ErrorStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ApiResponse<?> handleGeneralException(GeneralException e) {
        BaseErrorCode errorCode = e.getCode();
        ErrorReasonDTO reason = errorCode.getReason();
        return ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleUnexpectedException(Exception e) {
        e.printStackTrace(); // 서버 로그 디버깅용
        return ApiResponse.onFailure(
                ErrorStatus._INTERNAL_SERVER_ERROR.getReason().getCode(),
                "예상치 못한 에러가 발생했습니다.",
                null
        );
    }
}
