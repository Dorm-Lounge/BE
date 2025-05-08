package dorm.lounge.global.exception;

import dorm.lounge.global.BaseErrorCode;
import lombok.Getter;

@Getter
public abstract class GeneralException extends RuntimeException {

    private final BaseErrorCode code;

    protected GeneralException(BaseErrorCode code) {
        super(code.getReason().getMessage());
        this.code = code;
    }
}
