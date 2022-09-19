package com.studies.wscar.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.studies.wscar.dto.CarDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureWireMock(port=8084)
@TestPropertySource(properties = {
        "clients.carInfoURL=http://localhost:8084/v1/carInfos",
        "clients.carReviewsURL=http://localhost:8084/v1/car-reviews",
}) 
public class CarControllerIT {

    
    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    public void retrieveTest() {
        
        WireMock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/v1/carInfos/fixed-id"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("carinfo.json")
                    )
                );
        
        WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/v1/car-reviews"))
                .willReturn(
                        WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBodyFile("carreviews.json")
                        )
                );
        
        webTestClient.get()
            .uri("/v1/cars/{id}", "fixed-id")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CarDTO.class)
            .consumeWith(result->{
                CarDTO responseBody = result.getResponseBody();
                assertEquals("model abc", responseBody.getInfo().getModel());
                assertNotNull(responseBody.getReviews());
                assertEquals(2,responseBody.getReviews().size());
                
            });
    }
    
    @Test
    public void retrieveTestInvalidInfoId() {
        
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/v1/carInfos/fixed-id"))
                .willReturn(
                        WireMock.aResponse()
                            .withStatus(HttpStatus.NOT_FOUND.value())
                        )
                );
        
        webTestClient.get()
            .uri("/v1/cars/{id}", "fixed-id")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(String.class)
            .isEqualTo("Car Info not found for id fixed-id");
        
    }
    
    @Test
    public void retrieveTestInvalidReviewId() {
        
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/v1/carInfos/fixed-id"))
                    .willReturn(
                        WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBodyFile("carinfo.json")
                        )
                    );
        
        WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/v1/car-reviews"))
                .willReturn(
                        WireMock.aResponse()
                            .withStatus(HttpStatus.NOT_FOUND.value())
                        )
                );
        
        webTestClient.get()
            .uri("/v1/cars/{id}", "fixed-id")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CarDTO.class)
            .consumeWith(result->{
                CarDTO responseBody = result.getResponseBody();
                assertEquals("model abc", responseBody.getInfo().getModel());
                assertNotNull(responseBody.getReviews());
                assertEquals(0, responseBody.getReviews().size());
                
            });
        
    }
    
    @Test
    public void retrieveTestInfoWsServerError() {
        
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/v1/carInfos/fixed-id-info-server-error"))
                .willReturn(
                        WireMock.aResponse()
                            .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .withBody("INTERNAL_SERVER_ERROR")
                        )
                );
        
        webTestClient.get()
            .uri("/v1/cars/{id}", "fixed-id-info-server-error")
            .exchange()
            .expectStatus().is5xxServerError()
            .expectBody(String.class)
            .isEqualTo("INTERNAL_SERVER_ERROR");
        
        WireMock.verify(4, WireMock.getRequestedFor(WireMock.urlEqualTo("/v1/carInfos/fixed-id-info-server-error")));
        
    }
    

    @Test
    public void retrieveTestReviewWsServerError() {
        
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/v1/carInfos/fixed-id-reviews-server-error"))
                    .willReturn(
                        WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBodyFile("carinfo.json")
                        )
                    );
        
        WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/v1/car-reviews"))
                .willReturn(
                        WireMock.aResponse()
                            .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .withBody("INTERNAL_SERVER_ERROR")
                        )
                );
        
        webTestClient.get()
            .uri("/v1/cars/{id}", "fixed-id-reviews-server-error")
            .exchange()
            .expectStatus().is5xxServerError()
            .expectBody(String.class)
            .isEqualTo("INTERNAL_SERVER_ERROR");
        
        WireMock.verify(4, WireMock.getRequestedFor(WireMock.urlEqualTo("/v1/car-reviews?carInfoId=fixed-id-reviews-server-error")));
        
    }
}
