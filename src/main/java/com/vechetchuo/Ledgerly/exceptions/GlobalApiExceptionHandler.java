package com.vechetchuo.Ledgerly.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

@ControllerAdvice
public class GlobalApiExceptionHandler {

    private ResponseEntity<Map<String, Object>> json(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Map.of(
                        "code", status.value(),
                        "message", message
                ));
    }

    // 404: wrong URL
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        return json(HttpStatus.NOT_FOUND, "Not Found");
    }

    // 405: wrong HTTP method for the endpoint
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return json(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed");
    }

    // 400: missing required parameters or bad body
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class
    })
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
        return json(HttpStatus.BAD_REQUEST, "Bad Request");
    }

    // Fallback: 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAny(Exception ex) {
        return json(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}

