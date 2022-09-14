package com.studies.wscarinfo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.studies.wscarinfo.dto.CarDTO;
import com.studies.wscarinfo.services.CarInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class CarInfoController {
    
    private CarInfoService service;
    
    @Autowired
    public CarInfoController(CarInfoService carInfoService) {
        this.service = carInfoService;
    }
    

    @PostMapping("/carInfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CarDTO> create(@RequestBody @Valid CarDTO car) {
        return service.create(car);
    }
    
    @GetMapping("/carInfos")
    public Flux<CarDTO> retrieve(){
        return service.retrieve();
    }
    
    @GetMapping("/carInfos/{id}")
    public Mono<ResponseEntity<CarDTO>> retrieveById(@PathVariable String id){
        return service.retrieveById(id)
                .map(car -> ResponseEntity.ok().body(car))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/carInfos/{id}")
    public Mono<ResponseEntity<CarDTO>> update(@PathVariable String id, @RequestBody CarDTO carInfo) {
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
