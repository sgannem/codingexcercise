package com.claritysystemsinc.codeassignment.utils;

import com.claritysystemsinc.codeassignment.common.dto.ResponseDto;
import com.claritysystemsinc.codeassignment.common.dto.SuccessResponseDto;

public class ResponseDtoUtils {

    private static final String DEFAULT_SUCCESS_CODE = "CSI_0000";
    private static final String DEFAULT_ERROR_CODE = "CSI_0001";



    public static <T, E> ResponseDto<T, E> getSuccessResponseDtoWithDataAndMessage(T data, String message) {
        SuccessResponseDto<T, E> successResponseDto = new SuccessResponseDto(data, message, DEFAULT_SUCCESS_CODE);
        return successResponseDto;
    }

    public static <T, E> ResponseDto<T, E> getErrorResponseDtoWithErrorData(String message, E error) {
        SuccessResponseDto<T, E> successResponseDto = new SuccessResponseDto(
                null,
                message, DEFAULT_ERROR_CODE, error);
        return successResponseDto;
    }
}
