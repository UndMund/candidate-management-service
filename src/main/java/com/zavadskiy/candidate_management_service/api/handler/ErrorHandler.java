package com.zavadskiy.candidate_management_service.api.handler;

import com.zavadskiy.candidate_management_service.service.exception.ApiError;
import com.zavadskiy.candidate_management_service.service.exception.BaseException;
import com.zavadskiy.candidate_management_service.service.validation.ValidationErrorResponse;
import com.zavadskiy.candidate_management_service.service.validation.Violation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        var violations = e.getConstraintViolations()
                .stream()
                .map(violation -> new Violation(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                )).toList();
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var violations = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new Violation(
                        error.getField(),
                        error.getDefaultMessage()
                )).toList();
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(BaseException.class)
    ResponseEntity<ApiError> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatus());
    }
}
