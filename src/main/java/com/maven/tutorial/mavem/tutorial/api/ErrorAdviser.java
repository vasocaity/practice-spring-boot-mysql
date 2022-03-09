package com.maven.tutorial.mavem.tutorial.api;

import com.maven.tutorial.mavem.tutorial.exception.BaseException;
import com.maven.tutorial.mavem.tutorial.exception.NoDataFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorAdviser {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.error = e.getMessage();
        errorResponse.status = HttpStatus.EXPECTATION_FAILED.value();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataFoundException(
            NoDataFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Not found");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Data
    public static  class ErrorResponse {
        private LocalDateTime time = LocalDateTime.now();
        private  int status;
        private  String error;
    }
}
