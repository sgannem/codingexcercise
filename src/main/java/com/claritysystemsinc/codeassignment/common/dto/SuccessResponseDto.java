package com.claritysystemsinc.codeassignment.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseDto<T, E> implements ResponseDto {

    private String code = null;
    private T data;
    private String message = null;
    private long timestamp;
    private E errorData;

    public SuccessResponseDto() {
        this(null, "Success");
    }

    public SuccessResponseDto(T data) {
        this(data, "Success");
    }

    public SuccessResponseDto(String message) {
        this.message = message;
    }

    public SuccessResponseDto(T data2, String successMessage) {
        this.data = data2;
        this.message = successMessage;
        this.timestamp = System.currentTimeMillis();
    }

    public SuccessResponseDto(T data2, String successMessage, String  code, E errorData) {
        this.data = data2;
        this.message = successMessage;
        this.timestamp = System.currentTimeMillis();
        this.code = code;
        this.errorData = errorData;
    }

    public SuccessResponseDto(T data2, String successMessage, String code) {
        this(data2, successMessage, code, null);
    }

}
