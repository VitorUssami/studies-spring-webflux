package com.studies.reactive_reactor.b.operators.mono.flatmapmany;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFlatMapManyOperator {

    public Flux<String> monoFlatMapManyOperator() {

        return Mono.just("ABC")
                .flatMapMany(this::split)
                .log();
    }
    
    private Flux<String> split(String string){
        return Flux.fromArray(string.split(""));
    }
}
