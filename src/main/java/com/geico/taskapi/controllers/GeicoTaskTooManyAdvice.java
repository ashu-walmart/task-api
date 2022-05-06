package com.geico.taskapi.controllers;

import com.geico.taskapi.domain.exception.GeicoTaskNotFoundException;
import com.geico.taskapi.domain.exception.TooManyOpenHighTasksException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeicoTaskTooManyAdvice {

    @ExceptionHandler(TooManyOpenHighTasksException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    String geicoTooManyTasksHandler(GeicoTaskNotFoundException exception) {
        return exception.getMessage();
    }
}
