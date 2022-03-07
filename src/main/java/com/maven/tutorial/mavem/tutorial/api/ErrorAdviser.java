package com.maven.tutorial.mavem.tutorial.api;

import com.maven.tutorial.mavem.tutorial.exception.BaseException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorAdviser {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.error = e.getMessage();
        errorResponse.status = HttpStatus.EXPECTATION_FAILED.value();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
    }

    @Data
    public static  class ErrorResponse {
        private LocalDateTime time = LocalDateTime.now();
        private  int status;
        private  String error;
    }
}
