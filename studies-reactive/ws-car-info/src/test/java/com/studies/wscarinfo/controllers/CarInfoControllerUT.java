package com.studies.wscarinfo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.studies.wscarinfo.domain.CarInfo;
import com.studies.wscarinfo.dto.CarInfoDTO;
import com.studies.wscarinfo.services.CarInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = CarInfoController.class)
@AutoConfigureWebClient
public class CarInfoControllerUT {

    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private CarInfoService service;

    
    @Test
    public void createTest() {
        
        Mono<CarInfoDTO> mockedMono = Mono.just(CarInfoDTO.builder().carInfoId("id").model("model1").modelDescription("desc1")
                .color("white").price(20.0).modelDate(2022).build());
        Mockito.when(service.create(Mockito.any())).thenReturn(mockedMono);

        CarInfo carTosend = CarInfo.builder().model("model createTest").modelDescription("desc").color("black")
                .price(10.0).modelDate(2022).build();

        webTestClient
            .post().uri("/v1/carInfos")
            .bodyValue(carTosend)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(CarInfoDTO.class)
            .consumeWith(result -> {
                CarInfoDTO carResponse = result.getResponseBody();
                assertNotNull(carResponse.getCarInfoId());
                assertEquals("id", carResponse.getCarInfoId());
            });
    }
    
    @Test
    public void retrieveTest() {

        List<CarInfoDTO> list = List.of(
                CarInfoDTO.builder().model("model").modelDescription("desc").color("black").price(10.0).modelDate(2022)
                        .build(),
                        CarInfoDTO.builder().carInfoId("fixedId").model("model1").modelDescription("desc1").color("white")
                        .price(20.0).modelDate(2022).build());

        Mockito.when(service.retrieve()).thenReturn(Flux.fromIterable(list));
        
        webTestClient
            .get().uri("/v1/carInfos")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(CarInfoDTO.class)
            .hasSize(2);
    }
    
    @Test
    public void retrieveByIdTest() {
        
        Mono<CarInfoDTO> mockedMono = Mono.just(CarInfoDTO.builder().carInfoId("fixedId").model("model1")
                .modelDescription("desc1").color("white").price(20.0).modelDate(2022).build());
        Mockito.when(service.retrieveById(Mockito.any())).thenReturn(mockedMono);
        
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
    public void updateTest() {
        
        Mono<CarInfoDTO> mockedMono = Mono.just(CarInfoDTO.builder().carInfoId("id").model("model update")
                .modelDescription("desc1").color("white").price(20.0).modelDate(2022).build());
        Mockito.when(service.update(Mockito.any(), Mockito.isA(CarInfoDTO.class))).thenReturn(mockedMono);
        
        CarInfoDTO car = CarInfoDTO.builder().model("model update").modelDescription("desc update").color("black update")
                .price(10.0).modelDate(2022).build();
        
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
    public void deleteTest() {
        
        Mockito.when(service.delete(Mockito.any())).thenReturn(Mono.empty());
        
        webTestClient
            .delete().uri("/v1/carInfos/{id}", "fixedId")
            .exchange()
            .expectStatus().isNoContent();
    }
}
