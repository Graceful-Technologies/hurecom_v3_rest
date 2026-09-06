package com.gt.hurecom.exception;

import java.io.Serial;

public class HurecomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorMessage;

    public HurecomException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public HurecomException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
