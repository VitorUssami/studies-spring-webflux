package com.studies.wscarinfo.exception.handler;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {
    
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleWebExchangeBindException(WebExchangeBindException ex){
        
        String errors = ex.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(","));
        log.error("WebExchangeBindException: {} ", errors, ex);
        
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);
    }

}
