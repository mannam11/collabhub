package com.tool.collabhub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CollabRequestExceptionHandler {

    @ExceptionHandler(InvalidProjectCollabRequestException.class)
    public ResponseEntity<?> handleInvalidProjectCollabRequestException(InvalidProjectCollabRequestException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
