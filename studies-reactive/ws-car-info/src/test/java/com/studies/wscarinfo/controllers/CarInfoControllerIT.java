package com.studies.wscarinfo.controllers;

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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.studies.wscarinfo.domain.CarInfo;
import com.studies.wscarinfo.dto.CarInfoDTO;
import com.studies.wscarinfo.repositories.CarInfoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@TestMethodOrder(OrderAnnotation.class)
public class CarInfoControllerIT {

    @Autowired
    private CarInfoRepository repo;
    
    @Autowired
    private WebTestClient webTestClient;
    
    
    @BeforeEach
    public void beforeEach() {
        
        List<CarInfo> list = List.of(
                CarInfo.builder().model("model").modelDescription("desc").color("black").price(10.0).modelDate(2022)
                        .build(),
                CarInfo.builder().carInfoId("fixedId").model("model1").modelDescription("desc1").color("white")
                        .price(20.0).modelDate(2022).build());

        repo.saveAll(list).blockLast();
    }
    
    @AfterEach
    public void afterEach() {
        repo.deleteAll().block();
    }
    
    @Test
    public void createTest() {
        
        CarInfoDTO car = CarInfoDTO.builder().model("model createTest").modelDescription("desc").color("black").price(10.0).modelDate(2022).build();
        
        webTestClient
            .post().uri("/v1/carInfos")
            .bodyValue(car)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(CarInfoDTO.class)
            .consumeWith(result -> {
                CarInfoDTO carResponse = result.getResponseBody();
                assertNotNull(carResponse.getCarInfoId());
            });
    }
    
    @Test
    public void createInvalidDtoTest() {
        
        CarInfo carTosend = CarInfo.builder().modelDescription("desc").color("black")
                .modelDate(1).build();

        webTestClient
            .post().uri("/v1/carInfos")
            .bodyValue(carTosend)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
            .expectBody(String.class)
            .consumeWith(response-> {
                assertNotNull(response.getResponseBody());
//                System.out.println(response.getResponseBody());   
            });
        
    }
    
    @Test
    @Order(0)
    public void retrieveTest() {
        
        webTestClient
            .get().uri("/v1/carInfos")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(CarInfoDTO.class)
            .hasSize(2);
    }
    
    @Test
    public void retrieveByIdTest() {
        
        webTestClient
            .get().uri("/v1/carInfos/{id}", "fixedId")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(CarInfoDTO.class)
            .consumeWith(result -> {
                CarInfoDTO carResponse = result.getResponseBody();
                assertNotNull(carResponse);
                assertEquals(carResponse.getModel(), "model1");
            });
    }
    
    @Test
    public void retrieveByIdNotFoundTest() {
        
        webTestClient
        .get().uri("/v1/carInfos/{id}", "not-found")
        .exchange()
        .expectStatus().isNotFound();
    }
    
    @Test
    public void retrieveByIdTestJsonPath() {
        
        webTestClient
            .get().uri("/v1/carInfos/{id}", "fixedId")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody()
            .jsonPath("$.model").isEqualTo("model1");
    }
    
    
    @Test
    public void updateTest() {
        
        CarInfo car = CarInfo.builder().model("model update").modelDescription("desc update").color("black update").price(10.0).modelDate(2022).build();
        
        webTestClient
            .put().uri("/v1/carInfos/{id}", "fixedId")
            .bodyValue(car)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(CarInfoDTO.class)
            .consumeWith(result -> {
                CarInfoDTO carResponse = result.getResponseBody();
                assertNotNull(carResponse);
                assertEquals(carResponse.getModel(), "model update");
            });
    }
    
    @Test
    public void updateNotFoundTest() {
        
        CarInfo car = CarInfo.builder().model("model update").modelDescription("desc update").color("black update").price(10.0).modelDate(2022).build();
        
        webTestClient
            .put().uri("/v1/carInfos/{id}", "not-found")
            .bodyValue(car)
            .exchange()
            .expectStatus().isNotFound();
    }
    
    @Test
    @Order(Integer.MAX_VALUE)
    public void deleteTest() {
        
        webTestClient
            .delete().uri("/v1/carInfos/{id}", "fixedId")
            .exchange()
            .expectStatus().isNoContent();
    }
}
