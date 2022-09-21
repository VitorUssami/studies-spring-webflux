package com.studies.reactivereactor.b.operators.flatmap;

import org.junit.jupiter.api.Test;

import com.studies.reactivereactor.b.operators.flatmap.FlatMapOperator;

import reactor.test.StepVerifier;

public class FlatMapOperatorTest {

    
    @Test
    public void flatmapOperatorTest() {
        
        StepVerifier.create(new FlatMapOperator().flatmapOperator())
                    .expectNext("A","B","B","C","C","C")
                    .verifyComplete();
    }
    
    @Test
    public void flatmapOperatorIntegerTest() {
        
        StepVerifier.create(new FlatMapOperator().flatmapOperatorInteger())
                    .expectNext(1,2,3)
                    .verifyComplete();
    }
    
    @Test
    public void flatmapOperatorWithDelayTest() {
        
        StepVerifier.create(new FlatMapOperator().flatmapOperatorWithDelay())
                    .expectNextCount(6)
                    .verifyComplete();
    }
}
