package com.alkomis.shop.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Class represents custom error object, which could be used in place of standard error object.
 */
@Data
public class CustomApiErrorMessage {

    /**
     * Http status of error.
     */
    private HttpStatus httpStatus;

    /**
     * General error message.
     */
    private String message;

    /**
     * List of error details.
     */
    private List<String> errors;

    /**
     * Constructs a new error object with status code, general message and full list of detailed error messages.
     *
     * @param httpStatus status code for new error object.
     * @param message    general error message for new error object.
     * @param errors     list of detailed error messages specifying what was cause of an error.
     */
    public CustomApiErrorMessage(HttpStatus httpStatus, String message, List<String> errors) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Constructs a new error object with status code, general message and detailed error message.
     *
     * @param httpStatus status code for new error object.
     * @param message    general error message for new error object.
     * @param error      detailed error message specifying cause of an error.
     */
    public CustomApiErrorMessage(HttpStatus httpStatus, String message, String error) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = List.of(error);
    }
}