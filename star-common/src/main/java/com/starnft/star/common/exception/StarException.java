package com.starnft.star.common.exception;


public class StarException extends RuntimeException {
    private StarError starError;
    private String starErrorCode;
    private String exMessage;

    public StarException() {
        super();
    }

    public StarException(String message) {
        super(message);
    }

    public StarException(StarError starError, String... exMessage) {
        super(starError.getErrorMessage());
        this.starError = starError;

        if (exMessage.length > 0) {
            this.exMessage = exMessage[0];
        }
    }

    public StarException(String code, String exMessage) {
        super(exMessage);
        this.starErrorCode = code;
        this.exMessage = exMessage;
    }

    public String getExMessage() {
        return exMessage;
    }

    public StarError getArkError() {
        return starError;
    }

    public String getArkErrorCode() {
        return starErrorCode;
    }

}