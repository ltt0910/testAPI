package com.example.test3.exception;

public class FieldErrorException extends UserInfoException {
    private int code;
    private String message;

    public FieldErrorException(String message, int code) {
        super(message, code);
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
