package com.ejercicio.exceptions;

public class CannotSaveException extends RuntimeException {
    public CannotSaveException() {
    }

    public CannotSaveException(String message) {
        super(message);
    }
}
