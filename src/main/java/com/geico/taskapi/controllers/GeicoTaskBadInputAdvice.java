package com.geico.taskapi.controllers;

import com.geico.taskapi.domain.exception.BadTaskInputException;
import com.geico.taskapi.domain.exception.GeicoTaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeicoTaskBadInputAdvice {

    @ExceptionHandler(BadTaskInputException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String geicoBadInputHandler(GeicoTaskNotFoundException exception) {
        return exception.getMessage();
    }
}
