package com.example.test3.exception;

import com.example.test3.utils.CheckVaildate;

public class FieldNoVaildException extends Exception {
    public FieldNoVaildException(String message) {
        super(message);
    }
    public static void validate(String user) throws FieldNoVaildException {
        if (!CheckVaildate.checkVaild(user)) {
            throw new FieldNoVaildException("not valid");
        } else {
            System.out.println("welcome to vote");
        }
    }
}
