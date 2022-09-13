package com.studies.reactive_reactor.b.operators.concatmap;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class ConcatMapOperatorTest {

    
    @Test
    public void concatMapTest() {
        
        StepVerifier.create(new ConcatMapOperator().concatMap())
            .expectNext("A","B","B","C","C","C")
            .verifyComplete();
    }
}
