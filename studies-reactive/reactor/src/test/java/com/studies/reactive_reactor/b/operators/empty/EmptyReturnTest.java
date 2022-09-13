package com.studies.reactive_reactor.b.operators.empty;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class EmptyReturnTest {

    
    @Test
    public void defaultIfEmptyTest() {
        
        StepVerifier.create(new EmptyReturn().defaultIfEmpty())
                    .expectNext("default")
                    .verifyComplete();
    }
    
    @Test
    public void switchIfEmptyTest() {
        
        StepVerifier.create(new EmptyReturn().switchIfEmpty())
        .expectNext("7-DEFAULT")
        .verifyComplete();
    }
}
