package com.cloudcode.springcloud.exception;

import com.cloudcode.springcloud.model.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetail> handleAllException(Exception ex, WebRequest req) {
        return new ResponseEntity<ErrorDetail>(ErrorDetail.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(ex.getMessage()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppCustomException.class)
    public final ResponseEntity<ErrorDetail> handleCustomException(AppCustomException ex, WebRequest req) {
        return new ResponseEntity<ErrorDetail>(ErrorDetail.builder()
                .status(HttpStatus.BAD_REQUEST.value()).error(ex.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
    }

}
