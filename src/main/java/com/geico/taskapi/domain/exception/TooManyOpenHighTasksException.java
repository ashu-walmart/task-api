package com.geico.taskapi.domain.exception;

public class TooManyOpenHighTasksException extends RuntimeException {
    public TooManyOpenHighTasksException(String msg) {
        super(msg);
    }
}
