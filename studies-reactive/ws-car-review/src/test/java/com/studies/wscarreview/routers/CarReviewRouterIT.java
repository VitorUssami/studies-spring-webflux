package com.studies.wscarreview.routers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.studies.wscarreview.domain.CarReview;
import com.studies.wscarreview.dto.CarReviewDTO;
import com.studies.wscarreview.repositories.CarReviewRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@TestMethodOrder(OrderAnnotation.class)
public class CarReviewRouterIT {

    
    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    private CarReviewRepository repo;
    
    @BeforeEach
    public void beforeEach() {
        
        List<CarReview> list = List.of(
                CarReview.builder().carInfoId("1").comment("abc 123").rating(10.0).build(),
                CarReview.builder().carInfoId("1").comment("abc 123").rating(9.0).build(),
                CarReview.builder().carReviewId("fixedId").carInfoId("2").comment("abc 123").rating(5.0).build(),
                CarReview.builder().carInfoId("3").comment("abc 123").rating(8.0).build()
                );

        repo.saveAll(list).blockLast();
    }
    
    @AfterEach
    public void afterEach() {
        repo.deleteAll().block();
    }
    
    @Test
    public void createTest() {
        
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
    @Order(0)
    public void retrieveTest() {
        
        webTestClient
            .get().uri("/v1/car-reviews")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(CarReviewDTO.class)
            .hasSize(4);
    }
    
    @Test
    public void retrieveByIdTest() {
        
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
    @Order(Integer.MAX_VALUE)
    public void deleteTest() {
        
        webTestClient
            .delete().uri("/v1/car-reviews/{id}", "fixedId")
            .exchange()
            .expectStatus().isNoContent();
    }
}
