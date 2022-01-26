package com.example.test3.enums;

import org.springframework.http.HttpStatus;

public enum HttpStatusCustom {

    SUCCESS(200, HttpStatus.Series.SUCCESSFUL, "SUCCESS"),
    UNAUTHORIZED(401, HttpStatus.Series.CLIENT_ERROR, "Unauthorized"),
    PAYMENT_REQUIRED(402, HttpStatus.Series.CLIENT_ERROR, "Payment Required"),
    FORBIDDEN(403, HttpStatus.Series.CLIENT_ERROR, "Forbidden"),
    NOT_FOUND(404, HttpStatus.Series.CLIENT_ERROR, "Not Found"),
    INTERNAL_SERVER_ERROR(500, HttpStatus.Series.SERVER_ERROR, "Internal Server Error"),
    NOT_IMPLEMENTED(501, HttpStatus.Series.SERVER_ERROR, "Not Implemented"),
    INVALID_USER_INFO(900,HttpStatus.Series.CLIENT_ERROR,"Field Invaild"),
    FIELD_HAVE_CHAR(901,HttpStatus.Series.CLIENT_ERROR,"Filed Have Character"),
    USER_EXSITS(902,HttpStatus.Series.CLIENT_ERROR,"User exsits"),
    ;
    private static final HttpStatusCustom[] VALUES = values();
    private final int value;
    private final HttpStatus.Series series;
    private final String reasonPhrase;


    private HttpStatusCustom(int value, HttpStatus.Series series, String reasonPhrase) {
        this.value = value;
        this.series = series;
        this.reasonPhrase = reasonPhrase;
    }
    public HttpStatus.Series series() {
        return this.series;
    }
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
    public int value() {
        return this.value;
    }



}
