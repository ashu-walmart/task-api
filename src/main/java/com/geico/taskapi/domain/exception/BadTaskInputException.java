package com.geico.taskapi.domain.exception;

public class BadTaskInputException extends RuntimeException {
    public BadTaskInputException(String s) {
        super(s);
    }
}
