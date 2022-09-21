package com.studies.reactivereactor.b.operators.mono.flatmap;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.studies.reactivereactor.b.operators.mono.flatmap.MonoFlatMapOperator;

import reactor.test.StepVerifier;

public class MonoFlatMapOperatorTest {
    
    @Test
    public void monoFlatmapOperatorTest() {
        
        StepVerifier.create(new MonoFlatMapOperator().monoFlatmapOperator())
            .expectNext(List.of("A","B","C"))
            .verifyComplete();
    }

}
