package com.studies.reactive_reactor.b.operators.mono.flatmap;

import java.util.List;

import reactor.core.publisher.Mono;

public class MonoFlatMapOperator {

    public Mono<List<String>> monoFlatmapOperator() {

        return Mono.just("ABC")
                .flatMap(this::split)
                .log();
    }
    
    private Mono<List<String>> split(String string){
        return Mono.just(List.of(string.split("")));
    }
}
