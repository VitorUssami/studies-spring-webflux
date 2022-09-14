package com.studies.reactivereactor.a.first.flux;

import org.junit.jupiter.api.Test;

import com.studies.reactivereactor.a.first.flux.FirstFlux;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FirstFluxTest {

    
    @Test
    public void firstTestValidateValues() {
        
        
        Flux<String> namesFlux = new FirstFlux().namesFlux();
        
        StepVerifier.create(namesFlux)
                    .expectNext("A","B","C")
                    .verifyComplete();
        
    }
    
    @Test
    public void firstTestValidateLength() {
        
        
        Flux<String> namesFlux = new FirstFlux().namesFlux();
        
        StepVerifier.create(namesFlux)
                    .expectNextCount(3)
                    .verifyComplete();
    }
    
    @Test
    public void firstTestValidateMixed() {
        
        
        Flux<String> namesFlux = new FirstFlux().namesFlux();
        
        StepVerifier.create(namesFlux)
        .expectNext("A")
        .expectNextCount(2)
        .verifyComplete();
    }
}
