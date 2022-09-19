package com.studies.wscar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studies.wscar.clients.CarInfoRestClient;
import com.studies.wscar.clients.CarReviewsRestClient;
import com.studies.wscar.dto.CarDTO;
import com.studies.wscar.dto.CarReviewDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class CarController {

    private CarInfoRestClient infoClient;
    private CarReviewsRestClient reviewsClient;
    
    @Autowired
    public CarController(CarInfoRestClient infoClient, CarReviewsRestClient reviewsClient) {
        this.infoClient = infoClient;
        this.reviewsClient = reviewsClient;
    }

    @RequestMapping("/cars/{id}")
    public Mono<CarDTO> retrieve(@PathVariable String id) {
        
        Mono<CarDTO> retrieve = infoClient.retrieve(id)
                                    .flatMap(info-> {
                                        Mono<List<CarReviewDTO>> list = reviewsClient.retrieve(id).collectList();
                                        return list.map(reviews-> new CarDTO(info, reviews));
                                    });
        return retrieve;
    }
}
