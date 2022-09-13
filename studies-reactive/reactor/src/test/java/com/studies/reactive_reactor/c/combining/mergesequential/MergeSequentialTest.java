package com.studies.reactive_reactor.c.combining.mergesequential;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class MergeSequentialTest {

    
    @Test
    public void mergeSequentialTest() {
        
        StepVerifier.create(new MergeSequential().mergeSequential())
                    .expectNext("A","B","C","D","E","F")
                    .verifyComplete();
    }
}
