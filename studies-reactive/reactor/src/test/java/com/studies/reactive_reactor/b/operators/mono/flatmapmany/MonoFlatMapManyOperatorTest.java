package com.studies.reactive_reactor.b.operators.mono.flatmapmany;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class MonoFlatMapManyOperatorTest {
    
    @Test
    public void monoFlatmapOperatorTest() {
        
        StepVerifier.create(new MonoFlatMapManyOperator().monoFlatMapManyOperator())
            .expectNext("A","B","C")
            .verifyComplete();
    }

}
