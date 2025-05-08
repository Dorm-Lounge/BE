package dorm.lounge.global.exception;

import dorm.lounge.global.BaseErrorCode;

public class UserException extends GeneralException {
    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
