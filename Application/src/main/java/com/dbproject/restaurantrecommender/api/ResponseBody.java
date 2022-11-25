package com.dbproject.restaurantrecommender.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseBody {
    private String status;

    private Object data;

    private List<Map<String, Object>> errors;

    public ResponseBody(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    ResponseBody(String status, List<Map<String, Object>> errors) {
        this.status = status;
        this.errors = errors;
    }
}
