package com.studies.reactive_reactor.b.operators.filter;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class FilterOperatorTest {

    
    @Test
    public void filterOperatorByLengthTest() {
        
        StepVerifier.create(new FilterOperator().filterOperatorByLength(1))
                    .expectNextCount(2)
                    .verifyComplete();
    }
    
    @Test
    public void filterOperatorByLengthThenTransformTest() {
        
        StepVerifier.create(new FilterOperator().filterOperatorByLengthThenTransform(2))
                .expectNext("3-CCC")
                .verifyComplete();
    }
}
