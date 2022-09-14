package com.studies.reactivereactor.c.combining.mergesequential;

import org.junit.jupiter.api.Test;

import com.studies.reactivereactor.c.combining.mergesequential.MergeSequential;

import reactor.test.StepVerifier;

public class MergeSequentialTest {

    
    @Test
    public void mergeSequentialTest() {
        
        StepVerifier.create(new MergeSequential().mergeSequential())
                    .expectNext("A","B","C","D","E","F")
                    .verifyComplete();
    }
}
