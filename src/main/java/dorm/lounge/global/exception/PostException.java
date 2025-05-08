package dorm.lounge.global.exception;

import dorm.lounge.global.BaseErrorCode;

public class PostException extends GeneralException {
    public PostException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}