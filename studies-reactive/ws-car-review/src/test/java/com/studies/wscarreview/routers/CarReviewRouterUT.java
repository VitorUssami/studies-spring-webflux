package com.studies.wscarreview.routers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.studies.wscarreview.dto.CarReviewDTO;
import com.studies.wscarreview.exception.handler.ExceptionsHandler;
import com.studies.wscarreview.handlers.CarReviewsHandler;
import com.studies.wscarreview.services.CarReviewService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@AutoConfigureWebClient
@ContextConfiguration(classes = {CarReviewRouter.class, CarReviewsHandler.class, ExceptionsHandler.class})
public class CarReviewRouterUT {


    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private CarReviewService service;
    
    @Test
    public void createTest() {
        
        Mockito.when(service.create(Mockito.any()))
            .thenReturn(Mono.just(
                CarReviewDTO.builder().carReviewId("123").carInfoId("1").comment("abc 123").rating(10.0).build()));
        
        
        CarReviewDTO carReviewToSave = CarReviewDTO.builder().carInfoId("1").comment("abc 123").rating(10.0).build();
        webTestClient
            .post().uri("/v1/car-reviews")
            .bodyValue(carReviewToSave)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(CarReviewDTO.class)
            .consumeWith(result -> {
                CarReviewDTO carResponse = result.getResponseBody();
                assertNotNull(carResponse.getCarReviewId());
            });
        
    }
    
    @Test
    public void retrieveTest() {
        
        Mockito.when(service.retrieve(Mockito.any()))
            .thenReturn(Flux.fromIterable(List.of(
                CarReviewDTO.builder().carReviewId("123").carInfoId("1").comment("abc 123").rating(10.0).build(),
                CarReviewDTO.builder().carReviewId("456").carInfoId("1").comment("abc 123").rating(10.0).build()
                )));
    
        
        webTestClient
            .get().uri("/v1/car-reviews")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(CarReviewDTO.class)
            .hasSize(2);
    }
    
    @Test
    public void retrieveByIdTest() {
        
        Mockito.when(service.retrieve(Mockito.any()))
            .thenReturn(Flux.fromIterable(List.of(
                    CarReviewDTO.builder().carReviewId("123").carInfoId("1").comment("abc 123").rating(10.0).build(),
                    CarReviewDTO.builder().carReviewId("456").carInfoId("1").comment("abc 123").rating(10.0).build()
                    )));
            
        webTestClient
            .get()
            .uri(uriBuilder -> {
                return uriBuilder.path("/v1/car-reviews")
                        .queryParam("carInfoId", "1")
                        .build();
            })
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(CarReviewDTO.class)
            .hasSize(2);
    }
    
    @Test
    public void updateTest() {
        
        
        CarReviewDTO carReview = CarReviewDTO.builder().carReviewId("fixedId"). carInfoId("2").comment("abc 123 updated").rating(5.0).build();
        
        Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(Mono.just(carReview));
                
        webTestClient
            .put().uri("/v1/car-reviews/{id}", "fixedId")
            .bodyValue(carReview)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(CarReviewDTO.class)
            .consumeWith(result -> {
                CarReviewDTO carResponse = result.getResponseBody();
                assertNotNull(carResponse);
                assertEquals(carResponse.getComment(), "abc 123 updated");
            });
    }
    
    @Test
    public void deleteTest() {
        
        Mockito.when(service.delete(Mockito.any())).thenReturn(Mono.empty());
        
        webTestClient
            .delete().uri("/v1/car-reviews/{id}", "fixedId")
            .exchange()
            .expectStatus().isNoContent();
    }
}
