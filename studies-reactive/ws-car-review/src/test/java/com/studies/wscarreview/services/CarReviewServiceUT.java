package com.studies.wscarreview.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.studies.wscarreview.domain.CarReview;
import com.studies.wscarreview.dto.CarReviewDTO;
import com.studies.wscarreview.exception.CarReviewDataException;
import com.studies.wscarreview.repositories.CarReviewRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class CarReviewServiceUT {

    @Autowired
    private CarReviewService service;
    
    @MockBean
    private CarReviewRepository repo;
    

    @Test
    public void createTest() {
        
        Mockito.when(repo.save(Mockito.any()))
            .thenReturn(Mono.just(
                CarReview.builder().carReviewId("123").carInfoId("1").comment("abc 123").rating(10.0).build()));
    
        CarReviewDTO carReviewToSave = CarReviewDTO.builder().carInfoId("1").comment("abc 123").rating(10.0).build();
        Mono<CarReviewDTO> create = service.create(carReviewToSave);
        
        StepVerifier.create(create)
//                .expectNextCount(1)
                .assertNext(dto->assertEquals("123", dto.getCarReviewId()))
                .verifyComplete();
    }
    
    @Test
    public void createTestInvalidDto() {
        CarReviewDTO carReviewToSave = CarReviewDTO.builder().rating(10.0).build();
        Assertions.assertThrows(CarReviewDataException.class, () -> service.create(carReviewToSave));
    }
    
    @Test
    public void retrieveTest() {
        
        Mockito.when(repo.findAll())
            .thenReturn(Flux.fromIterable(List.of(
                    CarReview.builder().carReviewId("123").carInfoId("1").comment("abc 123").rating(10.0).build(),
                    CarReview.builder().carReviewId("456").carInfoId("1").comment("abc 123").rating(10.0).build()
                )));
    
        Flux<CarReviewDTO> retrieve = service.retrieve(Optional.empty());
        
        StepVerifier.create(retrieve)
                  .expectNextCount(2)
                  .verifyComplete();
    }
    
    @Test
    public void retrieveByIdTest() {
        
        Mockito.when(repo.findByCarInfoId(Mockito.any()))
            .thenReturn(Flux.fromIterable(List.of(
                    CarReview.builder().carReviewId("123").carInfoId("1").comment("abc 123").rating(10.0).build(),
                    CarReview.builder().carReviewId("456").carInfoId("1").comment("abc 123").rating(10.0).build(),
                    CarReview.builder().carReviewId("789").carInfoId("1").comment("abc 123").rating(10.0).build()
                    )));
        
        Flux<CarReviewDTO> retrieve = service.retrieve(Optional.of("456"));
        
        StepVerifier.create(retrieve)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void updateTest() {
        
        Mockito.when(repo.findById(Mockito.anyString()))
                .thenReturn(Mono.just(CarReview.builder().build()));
        
        Mockito.when(repo.save(Mockito.any()))
            .thenReturn(Mono.just(
                CarReview.builder().carReviewId("123").carInfoId("1").comment("abc 123 updated").rating(10.0).build()));
     
        CarReviewDTO carReview = CarReviewDTO.builder().carReviewId("fixedId"). carInfoId("2").comment("abc 123 updated").rating(5.0).build();
        
        Mono<CarReviewDTO> update = service.update("fixedId", carReview);
        
        StepVerifier.create(update)
            .assertNext(dto->assertEquals("abc 123 updated", dto.getComment()))
            .verifyComplete();
    }
    
    
    @Test
    public void deleteTest() {
        
        Mockito.when(repo.findById(Mockito.anyString()))
            .thenReturn(Mono.just(CarReview.builder().build()));
        
        Mockito.when(repo.delete(Mockito.any())).thenReturn(Mono.empty());
        
        Mono<Void> delete = service.delete("fixedId");
        StepVerifier.create(delete)
            .expectNextCount(0)
            .verifyComplete();
    }
}
