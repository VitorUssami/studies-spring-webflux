package com.studies.reactive_reactor.c.combining.merge;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class MergeTest {

    
    @Test
    public void mergeTest() {
        
        StepVerifier.create(new Merge().merge())
                    .expectNext("A","D","B","E","C","F")
                    .verifyComplete();
    }
    
    @Test
    public void mergeWithTest() {
        
        StepVerifier.create(new Merge().mergeWith())
                    .expectNext("A","D","B","E","C","F")
                    .verifyComplete();
    }
    
    @Test
    public void mergeWithOnMonoTest() {
        
        StepVerifier.create(new Merge().mergeWithOnMono())
                    .expectNext("A","D")
                    .verifyComplete();
    }
}
