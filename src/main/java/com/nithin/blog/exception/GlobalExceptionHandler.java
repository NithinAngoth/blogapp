package com.nithin.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nithin.blog.payloads.ApiResponse;

@RestControllerAdvice // To handle the all controllers exception globally via api`s
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // Handling ResourceNotFoundException globally
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Handling MethodArgumentNotValidException globally
    public ResponseEntity<Map<String, String>> handleMethodAgrsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String filedName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(filedName, message);
        });
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class) // Handling ResourceNotFoundException globally
    public ResponseEntity<ApiResponse> handleApiException(ApiException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
