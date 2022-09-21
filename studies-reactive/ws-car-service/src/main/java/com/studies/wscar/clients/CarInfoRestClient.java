package com.studies.wscar.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.studies.wscar.dto.CarInfoDTO;
import com.studies.wscar.utils.RetryUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CarInfoRestClient {

    private WebClient webClient;
    
    @Value("${clients.carInfoURL}")
    private String carInfoURL;
    
    @Autowired
    public CarInfoRestClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public Mono<CarInfoDTO> retrieve(String id){
        
//        RetryBackoffSpec retrySpecVar = Retry.fixedDelay(3 , Duration.ofSeconds(1))
//                                    .filter(exception -> exception instanceof CarInfoServerException)
//                                    .onRetryExhaustedThrow((backoffSpec, signal) -> Exceptions.propagate(signal.failure()));
        
        return webClient.get()
            .uri(carInfoURL+"/{id}", id)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, response->{
                
                if(response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                    return Mono.error(new CarInfoClientException("Car Info not found for id "+id , response.statusCode()));
                }
                return response.bodyToMono(String.class).flatMap(resp -> Mono.error(new CarInfoClientException(resp, response.statusCode())));
            })
            .onStatus(HttpStatus::is5xxServerError, response->{
                
                return response.bodyToMono(String.class).flatMap(resp -> Mono.error(new CarInfoServerException(resp)));
            })
            .bodyToMono(CarInfoDTO.class)
//            .retry() //retry once
//            .retry(3) //retry x times without delay time
//            .retryWhen(Retry.fixedDelay(3 , Duration.ofSeconds(1)))
//            .retryWhen(retrySpecVar) //using variable
            .retryWhen(RetryUtils.retrySpec()) 
            .log();
    }
    
    public Flux<CarInfoDTO> stream(){
        return webClient.get()
                .uri(carInfoURL+"/stream")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response->{
                    return response.bodyToMono(String.class).flatMap(resp -> Mono.error(new CarInfoClientException(resp, response.statusCode())));
                })
                .onStatus(HttpStatus::is5xxServerError, response->{
                    return response.bodyToMono(String.class).flatMap(resp -> Mono.error(new CarInfoServerException(resp)));
                })
                .bodyToFlux(CarInfoDTO.class)
                .retryWhen(RetryUtils.retrySpec()) 
                .log();
    }
    
    public static class CarInfoClientException extends RuntimeException {
        
        private static final long serialVersionUID = -8671339258919834955L;
        private HttpStatus httpStatus;
        
        public CarInfoClientException(String string, HttpStatus httpStatus) {
            super(string);
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }
    
    public static class CarInfoServerException extends RuntimeException {
        
        private static final long serialVersionUID = -8353267088336545970L;
        public CarInfoServerException(String string) {
            super(string);
        }
    }
}
