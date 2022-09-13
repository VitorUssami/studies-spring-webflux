package com.studies.reactive_reactor.b.operators.mono.flatmap;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class MonoFlatMapOperatorTest {
    
    @Test
    public void monoFlatmapOperatorTest() {
        
        StepVerifier.create(new MonoFlatMapOperator().monoFlatmapOperator())
            .expectNext(List.of("A","B","C"))
            .verifyComplete();
    }

}
