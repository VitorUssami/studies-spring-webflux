package com.studies.reactivereactor.b.operators.map;

import java.util.List;

import reactor.core.publisher.Flux;

public class MapOperator {

    public Flux<String> mapOperator_lowerToUpper() {

        return Flux.fromIterable(List.of("a", "b", "c"))
                .map(String::toUpperCase);
    }

}
