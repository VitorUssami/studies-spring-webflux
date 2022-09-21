package com.studies.reactivereactor.b.operators.transform;

import org.junit.jupiter.api.Test;

import com.studies.reactivereactor.b.operators.transform.TransformOperator;

import reactor.test.StepVerifier;

public class TransformOperatorTest {

    
    @Test
    public void transformOperator() {
        
        StepVerifier.create(new TransformOperator().transformOperator(2))
                    .expectNext("3-CCC")
                    .verifyComplete();
    }
}
