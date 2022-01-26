package com.example.test3.response;

import com.example.test3.model.UserModel;

import java.util.List;

public class ResponseCustom  {

    private int status;
    private int code;
    private Object data;
    private String message;

    public ResponseCustom(int status, int code, Object data, String message) {
        this.status = status;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseCustom() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
