package com.studies.reactivereactor.b.operators.flatmap;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FlatMapOperator {

    public Flux<String> flatmapOperator() {

        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .map(String::toUpperCase)
                .flatMap(this::split)
                .log();
    }
    
    public Flux<Integer> flatmapOperatorInteger() {
        
        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .map(String::toUpperCase)
                .flatMap(value -> Mono.just(value.length()))
                .log();
    }

    
    public Flux<String> flatmapOperatorWithDelay() {

        
        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .map(String::toUpperCase)
                .flatMap(this::splitWithDelay)                
                .log();
    }
    
    
    private Flux<String> split(String string){
        return Flux.fromArray(string.split(""));
    }
    
    private Flux<String> splitWithDelay(String string){
        
        int nextInt = new Random().nextInt(1000);
        return Flux.fromArray(string.split(""))
                .delayElements(Duration.ofMillis(nextInt));
    }
}
