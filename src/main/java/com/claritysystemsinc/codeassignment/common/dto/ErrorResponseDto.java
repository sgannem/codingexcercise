package com.claritysystemsinc.codeassignment.common.dto;

import lombok.Data;

@Data
public class ErrorResponseDto<T, E> implements ResponseDto {

    private String code = "";
    private T data;
    private String message;
    private long timestamp;
    private E errorData;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(String errorMessage) {
        this.message = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponseDto(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponseDto(String errorCode, String errorMessage, T data) {
        this.data = data;
        this.code = errorCode;
        this.message = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponseDto(String errorCode, String errorMessage, T data, E errorData) {
        this.data = data;
        this.code = errorCode;
        this.message = errorMessage;
        this.timestamp = System.currentTimeMillis();
        this.errorData = errorData;
    }

}
