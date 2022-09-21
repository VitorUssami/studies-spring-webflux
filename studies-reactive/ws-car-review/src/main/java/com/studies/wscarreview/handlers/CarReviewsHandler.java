package com.studies.wscarreview.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.studies.wscarreview.dto.CarReviewDTO;
import com.studies.wscarreview.services.CarReviewService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Component
public class CarReviewsHandler {
    
    private CarReviewService service;
    
    private Many<CarReviewDTO> reviewsSink = Sinks.many().replay().latest();
    
    @Autowired
    public CarReviewsHandler(CarReviewService service) {
        this.service = service;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        
        return request.bodyToMono(CarReviewDTO.class)
                .flatMap(dto -> service.create(dto))
                .doOnNext(dto-> reviewsSink.tryEmitNext(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto));
        
//        Mono<CarReviewDTO> mono = request.bodyToMono(CarReviewDTO.class);
//        return ServerResponse.status(HttpStatus.CREATED)
//                .body(BodyInserters.fromPublisher(
//                        mono.flatMap(dto -> service.create(dto)), CarReviewDTO.class));
    }

    public Mono<ServerResponse> retrieve(ServerRequest request) {
        
        Flux<CarReviewDTO> retrieve = service.retrieve(request.queryParam("carInfoId"));
        return ServerResponse.status(HttpStatus.CREATED).body(retrieve, CarReviewDTO.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        
        String id = request.pathVariable("id");
        return request.bodyToMono(CarReviewDTO.class)
            .flatMap(dto -> service.update(id, dto))
            .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        
        String id = request.pathVariable("id");
        return service.delete(id).then(ServerResponse.noContent().build());
    }

    public  Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_NDJSON)
                    .body(reviewsSink.asFlux(), CarReviewDTO.class);
    }

}
