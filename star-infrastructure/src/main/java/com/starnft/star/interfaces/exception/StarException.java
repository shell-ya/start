package com.starnft.star.interfaces.exception;


import com.starnft.star.interfaces.common.StarError;


public class StarException extends RuntimeException {
    private StarError arkError;
    private String arkErrorCode;
    private String exMessage;

    public StarException() {
        super();
    }

    public StarException(String message) {
        super(message);
    }

    public StarException(StarError arkError, String... exMessage) {
        super(arkError.getErrorMessage());
        this.arkError = arkError;

        if (exMessage.length > 0) {
            this.exMessage = exMessage[0];
        }
    }

    public StarException(String code, String exMessage) {
        super(exMessage);
        this.arkErrorCode = code;
        this.exMessage = exMessage;
    }

    public String getExMessage() {
        return exMessage;
    }

    public StarError getArkError() {
        return arkError;
    }

    public String getArkErrorCode() {
        return arkErrorCode;
    }

}