package com.studies.reactive_reactor.c.combining.merge;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Merge {

    
    public Flux<String> merge(){
        
        Flux<String> one = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        
        Flux<String> two = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(150));;
        
        return Flux.merge(one, two);
    }
    
    public Flux<String> mergeWith(){
        
        Flux<String> one = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        
        Flux<String> two = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(150));;
                
        return one.mergeWith(two);
    }
    
    public Flux<String> mergeWithOnMono(){
        
        Mono<String> one = Mono.just("A");
        Mono<String> two = Mono.just("D");
        
        return one.mergeWith(two);
    }
}
