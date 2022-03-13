package com.alkomis.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class, exception thrown when application tries to access database resource that does not exist, or was marked as deleted.
 * Also contains 404 response code.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends RuntimeException {

    /**
     * Exception message.
     */
    private String message;

    /**
     * Constructs a new ElementNotFound exception with specified detail message.
     *
     * @param message the detail message for exception. Can be used later.
     */
    public ElementNotFoundException(String message) {
        super(message);
    }
}
