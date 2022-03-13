package com.alkomis.shop.exceptions.handlers;

import com.alkomis.shop.exceptions.CustomApiErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Custom exception handler. Used to handle {@link MethodArgumentNotValidException} and {@link HttpMessageNotReadableException} which thrown when validation fails.
 */
@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>(); // Creating list of error messages.
        for (FieldError error : ex.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage()); //Add field error messages.
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage()); //Add global error messages.
        }

        CustomApiErrorMessage customApiError = new CustomApiErrorMessage(HttpStatus.BAD_REQUEST, "Validation failed", errors); //Create custom error object.

        // Perform logging for failed validation.
        log.error(customApiError.getMessage() + ". Details: " + errors);

        return new ResponseEntity<>(customApiError, HttpStatus.BAD_REQUEST); //Return custom error object and status code.
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomApiErrorMessage customApiError = new CustomApiErrorMessage
                (HttpStatus.BAD_REQUEST, "JSON Parse error",
                        Objects.requireNonNull(ex.getRootCause()).getMessage()); // Create custom error object.

        // Perform logging for parsing error.
        log.error(customApiError.getMessage() + ". Details: " + ex.getRootCause().getMessage());

        return new ResponseEntity<>(customApiError, status); //Return custom error object and status code.
    }
}

