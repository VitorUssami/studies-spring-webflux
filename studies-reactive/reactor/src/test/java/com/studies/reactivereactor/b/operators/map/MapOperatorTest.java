package com.studies.reactivereactor.b.operators.map;

import org.junit.jupiter.api.Test;

import com.studies.reactivereactor.b.operators.map.MapOperator;

import reactor.test.StepVerifier;

public class MapOperatorTest {

    
    @Test
    public void mapOperator_lowerToUpperTest() {
        
        
        StepVerifier.create(new MapOperator().mapOperator_lowerToUpper())
                    .expectNext("A", "B", "C")
                    .verifyComplete();
    }
}
