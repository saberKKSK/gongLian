package com.gonglian.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(200, "success", data);
    }

    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult<T>(500, message, null);
    }
} 