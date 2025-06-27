package com.tool.collabhub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DeveloperProfileExceptionHandler {

    @ExceptionHandler(InvalidDeveloperProfileRequestException.class)
    public ResponseEntity<?> handleInvalidDeveloperProfileRequestException(InvalidDeveloperProfileRequestException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
