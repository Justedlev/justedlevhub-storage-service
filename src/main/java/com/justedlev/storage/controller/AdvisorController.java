package com.justedlev.storage.controller;

import com.justedlev.storage.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;

@ControllerAdvice
public class AdvisorController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {EntityNotFoundException.class, FileNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleNotFountException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
