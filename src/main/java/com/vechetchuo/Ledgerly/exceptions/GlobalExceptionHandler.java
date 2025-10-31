package com.vechetchuo.Ledgerly.exceptions;

import com.vechetchuo.Ledgerly.utils.ApiResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Validation failed");

        return ApiResponse.failure(400, error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "HTTP method " + ex.getMethod() + " is not supported for this endpoint";
        return ApiResponse.failure(405, message);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse<String> handleNotFound(NoHandlerFoundException ex) {
        String message = "The requested URL " + ex.getRequestURL() + " was not found";
        return ApiResponse.failure(404, message);
    }

    // // You can add a generic handler for other unexpected errors
    // @ExceptionHandler(Exception.class)
    // public ApiResponse<String> handleGenericException(Exception ex) {
    //     // Log the full exception
    //     // e.g., log.error("An unexpected error occurred", ex);
    //     return ApiResponse.failure(500, "An internal server error occurred");
    // }
}

