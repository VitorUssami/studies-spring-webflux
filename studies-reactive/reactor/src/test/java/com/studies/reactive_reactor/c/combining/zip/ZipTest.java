package com.studies.reactive_reactor.c.combining.zip;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class ZipTest {

    
    @Test
    public void zipTest() {
        
        StepVerifier.create(new Zip().zip())
                    .expectNext("AD","BE","CF")
                    .verifyComplete();
    }
    
    @Test
    public void zipTupleTest() {
        
        StepVerifier.create(new Zip().zipTuple())
        .expectNext("ADG1","BEH2","CFI3")
        .verifyComplete();
    }
    
    @Test
    public void zipWithTest() {
        
        StepVerifier.create(new Zip().zipWith())
        .expectNext("AD","BE","CF")
        .verifyComplete();
    }
    
    @Test
    public void zipWithOnMonoTest() {
        
        StepVerifier.create(new Zip().zipWithOnMono())
            .expectNext("AD")
            .verifyComplete();
    }
}
