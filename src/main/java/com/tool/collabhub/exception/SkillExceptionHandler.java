package com.tool.collabhub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SkillExceptionHandler {

    @ExceptionHandler(InvalidSkillRequestException.class)
    public ResponseEntity<?> handleInvalidSkillRequestException(InvalidSkillRequestException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
