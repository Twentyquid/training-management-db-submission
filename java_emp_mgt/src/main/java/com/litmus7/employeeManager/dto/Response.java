package com.litmus7.employeeManager.dto;
import com.litmus7.employeeManager.constant.StatusCodes;

public class Response<T> {
    private String message;
    private T data;
    private int statusCode;

    public Response(String message, T data, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }


    public T getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    
    public static <T> Response<T> success(T data) {
        return new Response<>("Operation successful", data, StatusCodes.SUCCESS);
    }

    public static <T> Response<T> failure(String message) {
        return new Response<>(message, null, StatusCodes.FAILURE);
    }
}
