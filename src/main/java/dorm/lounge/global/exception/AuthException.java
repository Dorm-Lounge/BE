package dorm.lounge.global.exception;

import dorm.lounge.global.exception.GeneralException;
import dorm.lounge.global.status.ErrorStatus;

public class AuthException extends GeneralException {
    public AuthException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}

