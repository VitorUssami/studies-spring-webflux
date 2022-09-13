package com.studies.reactive_reactor.a.first.mono;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FirstMonoTest {

    @Test
    public void firstTestValidateValues() {
        
        
        Mono<String> nameMono = new FirstMono().nameMono();
        
        StepVerifier.create(nameMono)
                    .expectNext("A")
                    .verifyComplete();
        
    }
    
    @Test
    public void firstTestValidateLength() {
        
        
        Mono<String> nameMono = new FirstMono().nameMono();
        
        StepVerifier.create(nameMono)
                    .expectNextCount(1)
                    .verifyComplete();
    }
    
}
