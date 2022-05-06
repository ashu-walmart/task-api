package com.geico.taskapi.domain.exception;

public class GeicoTaskNotFoundException extends RuntimeException {
    public GeicoTaskNotFoundException(Long id) {
        super("Could not find task: " + id);
    }
}
