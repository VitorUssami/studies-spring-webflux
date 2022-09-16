package com.studies.wscarreview.exception.handler;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.studies.wscarreview.exception.CarReviewDataException;
import com.studies.wscarreview.exception.CarReviewNotFoundException;

import reactor.core.publisher.Mono;

@Component
public class ExceptionsHandler implements ErrorWebExceptionHandler{

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(ex.getMessage().getBytes());
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        if (ex instanceof CarReviewDataException) {
            status = HttpStatus.BAD_REQUEST;
        }
        if (ex instanceof CarReviewNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }
        
        exchange.getResponse().setStatusCode(status);
        return  exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

}
