package com.studies.wscar.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.studies.wscar.dto.CarReviewDTO;
import com.studies.wscar.utils.RetryUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CarReviewsRestClient {

    private WebClient webClient;
    
    @Value("${clients.carReviewsURL}")
    private String carReviewsURL;
    
    @Autowired
    public CarReviewsRestClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public Flux<CarReviewDTO> retrieve(String id){
        
        String uriString = UriComponentsBuilder.fromHttpUrl(carReviewsURL)
                .queryParam("carInfoId", id).buildAndExpand().toUriString();
        
        return webClient.get()
            .uri(uriString)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, response->{
                if(response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                    return Mono.empty();
                }
                return response.bodyToMono(String.class).flatMap(resp -> Mono.error(new CarReviewsClientException(resp, response.statusCode())));
            })
            .onStatus(HttpStatus::is5xxServerError, response->{
                
                return response.bodyToMono(String.class).flatMap(resp -> Mono.error(new CarReviewsServerException(resp)));
            })
            .bodyToFlux(CarReviewDTO.class)
            .retryWhen(RetryUtils.retrySpec()) 
            .log();
    }
    
    public static class CarReviewsClientException extends RuntimeException {
        
        private static final long serialVersionUID = -2189445859188967603L;
        private HttpStatus httpStatus;
        
        public CarReviewsClientException(String string, HttpStatus httpStatus) {
            super(string);
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }
    
    public static class CarReviewsServerException extends RuntimeException {
        private static final long serialVersionUID = -1266047226824398879L;
        public CarReviewsServerException(String string) {
            super(string);
        }
    }
}
