package com.studies.reactive_reactor.c.combining.concat;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class ConcatTest {

    
    @Test
    public void concatTest() {
        
        StepVerifier.create(new Concat().concat())
                    .expectNext("A","B","C","D","E","F")
                    .verifyComplete();
    }
    
    @Test
    public void concatWith() {
        
        StepVerifier.create(new Concat().concatWith())
                    .expectNext("A","B","C","D","E","F")
                    .verifyComplete();
    }
    
    @Test
    public void concatWithOnMonoTest() {
        
        StepVerifier.create(new Concat().concatWithOnMono())
                    .expectNext("A","D")
                    .verifyComplete();
    }
}
