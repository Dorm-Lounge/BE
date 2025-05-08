package dorm.lounge.global.exception;

import dorm.lounge.global.BaseErrorCode;

public class CommentException extends GeneralException {
    public CommentException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}