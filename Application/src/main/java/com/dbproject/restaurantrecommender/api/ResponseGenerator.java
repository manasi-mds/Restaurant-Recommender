package com.dbproject.restaurantrecommender.api;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseGenerator {
    public static final String STR_FAILURE = "FAILURE";
    public static final String STR_SUCCESS = "SUCCESS";

    public static ResponseBody createFailureResponse(String message, HttpStatus status, String developerMessage) {
        Map<String, Object> error = new HashMap<>() {
            {
                put("message", message);
                put("http_status", status);
            }
        };

        if (developerMessage != null) {
            error.put("developer_message", developerMessage);
        }

        List<Map<String, Object>> errors = new ArrayList<>();
        errors.add(error);

        return new ResponseBody(STR_FAILURE, errors);
    }

    public static ResponseBody createSuccessResponse(Object data) {
        return new ResponseBody(STR_SUCCESS, data);
    }

    public static ResponseBody createSuccessResponse() {
        return new ResponseBody(STR_SUCCESS, null);
    }
}
