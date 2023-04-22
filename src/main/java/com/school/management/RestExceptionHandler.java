package com.school.management;

import com.school.management.utils.ApiResponse;
import com.school.management.utils.CustomException;
import com.school.management.utils.ResourceNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage(), null).createResponse();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(ValidationException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()).createResponse();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> CustomException(CustomException ex) {
        return new ApiResponse<>(ex.getStatus(), ex.getErrorMessage()).createResponse();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> handleInternalServerError(Exception ex) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage()).createResponse();
    }
}