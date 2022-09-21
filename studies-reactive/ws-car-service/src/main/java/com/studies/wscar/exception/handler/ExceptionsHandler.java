package com.studies.wscar.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.studies.wscar.clients.CarInfoRestClient.CarInfoClientException;
import com.studies.wscar.clients.CarInfoRestClient.CarInfoServerException;
import com.studies.wscar.clients.CarReviewsRestClient.CarReviewsClientException;
import com.studies.wscar.clients.CarReviewsRestClient.CarReviewsServerException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler(CarInfoClientException.class)
    public ResponseEntity<String> handleCarInfoClientException(CarInfoClientException ex){
        
        log.error("CarInfoClientException: {} ", ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
    
    @ExceptionHandler(CarInfoServerException.class)
    public ResponseEntity<String> handleCarInfoServerException(CarInfoServerException ex){
        
        log.error("CarInfoClientException: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
    
    @ExceptionHandler(CarReviewsClientException.class)
    public ResponseEntity<String> handleCarReviewsClientException(CarReviewsClientException ex){
        
        log.error("CarReviewsClientException: {} ", ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
    
    @ExceptionHandler(CarReviewsServerException.class)
    public ResponseEntity<String> handleCarReviewsServerException(CarReviewsServerException ex){
        
        log.error("CarInfoClientException: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
    
}
