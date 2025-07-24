package com.litmus7.employeeManager.dto;

public class Response<T> {
    private String message;
    private T data;
    private boolean success;

    public Response(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }


    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    
    public static <T> Response<T> success(T data) {
        return new Response<>("Operation successful", data, true);
    }

    public static <T> Response<T> failure(String message) {
        return new Response<>(message, null, false);
    }
}
