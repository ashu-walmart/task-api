package com.geico.taskapi.controllers;

import com.geico.taskapi.domain.exception.GeicoTaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeicoTaskNotFoundAdvice {

    @ExceptionHandler(GeicoTaskNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String geicoTaskNotFoundHandler(GeicoTaskNotFoundException exception) {
        return exception.getMessage();
    }
}
