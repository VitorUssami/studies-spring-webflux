package com.studies.reactivereactor.b.operators.concatmap;

import org.junit.jupiter.api.Test;

import com.studies.reactivereactor.b.operators.concatmap.ConcatMapOperator;

import reactor.test.StepVerifier;

public class ConcatMapOperatorTest {

    
    @Test
    public void concatMapTest() {
        
        StepVerifier.create(new ConcatMapOperator().concatMap())
            .expectNext("A","B","B","C","C","C")
            .verifyComplete();
    }
}
