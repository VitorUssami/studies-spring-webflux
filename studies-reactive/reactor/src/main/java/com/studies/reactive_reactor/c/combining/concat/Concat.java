package com.studies.reactive_reactor.c.combining.concat;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Concat {

    
    public Flux<String> concat(){
        
        Flux<String> one = Flux.just("A","B","C");
        Flux<String> two = Flux.just("D","E","F");
        
        return Flux.concat(one, two);
    }
    
    public Flux<String> concatWith(){
        
        Flux<String> one = Flux.just("A","B","C");
        Flux<String> two = Flux.just("D","E","F");
        
        return one.concatWith(two);
    }
    
    public Flux<String> concatWithOnMono(){
        
        Mono<String> one = Mono.just("A");
        Mono<String> two = Mono.just("D");
        
        return one.concatWith(two);
    }
}
