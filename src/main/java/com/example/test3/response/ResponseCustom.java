package com.example.test3.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseCustom {
    public static ResponseEntity<Object> response(String message, HttpStatus status, Object responseObj,Integer integer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",status.value());
        map.put("message", message);
        map.put("status",integer);
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map,status);
    }

}
