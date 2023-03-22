package com.claritysystemsinc.codeassignment.config;

import com.claritysystemsinc.codeassignment.common.dto.ErrorResponseDto;
import com.claritysystemsinc.codeassignment.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> handleCancelOrderBadRequests(ServiceException ex) {
        log.info("#fileupoadexception:"+ex);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "CSI_0001",
                "file uploaded error",
                null,
                "invalid file format"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDto> handleMaxUploadSizeExceededException(ServiceException ex) {
        log.info("#maxuploadfile size exception:"+ex);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "CSI_0002",
                "max upload size exceeded exception",
                null,
                "max upload size exceeded exception"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

}
