package com.studies.wscarinfo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.studies.wscarinfo.dto.CarInfoDTO;
import com.studies.wscarinfo.services.CarInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@RestController
@RequestMapping("/v1")
public class CarInfoController {
    
    private CarInfoService service;
    
    private Many<CarInfoDTO> carsSink = Sinks.many().replay().latest();
//    private Many<CarInfoDTO> carsSink = Sinks.many().replay().all();
    
    @Autowired
    public CarInfoController(CarInfoService carInfoService) {
        this.service = carInfoService;
    }
    

    @PostMapping("/carInfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CarInfoDTO> create(@RequestBody @Valid CarInfoDTO car) {
        return service.create(car)
                .doOnNext(saved -> carsSink.tryEmitNext(saved));
    }
    
    @GetMapping("/carInfos")
    public Flux<CarInfoDTO> retrieve(){
        return service.retrieve();
    }
    
    @GetMapping("/carInfos/{id}")
    public Mono<ResponseEntity<CarInfoDTO>> retrieveById(@PathVariable String id){
        return service.retrieveById(id)
                .map(car -> ResponseEntity.ok().body(car))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    
//    @GetMapping(value="/carInfos/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @GetMapping(value="/carInfos/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<CarInfoDTO> stream(){
        return carsSink.asFlux();
    }

    @PutMapping("/carInfos/{id}")
    public Mono<ResponseEntity<CarInfoDTO>> update(@PathVariable String id, @RequestBody CarInfoDTO carInfo) {
        return service.update(id, carInfo)
                .map(car -> ResponseEntity.ok().body(car))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    
    @DeleteMapping("/carInfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }
    
}
