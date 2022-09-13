package com.studies.reactive_reactor.c.combining.zip;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Zip {

    
    public Flux<String> zip(){
        
        Flux<String> one = Flux.just("A","B","C");
        
        Flux<String> two = Flux.just("D","E","F");
        
        return Flux.zip(one, two, (a,b) -> a+b );
    }
    
    public Flux<String> zipTuple(){
        
        Flux<String> one = Flux.just("A","B","C");
        Flux<String> two = Flux.just("D","E","F");
        Flux<String> three = Flux.just("G","H","I");
        Flux<String> four = Flux.just("1","2","3");
        
        return Flux.zip(one, two, three, four)
                .map( t -> t.getT1()+t.getT2()+t.getT3()+t.getT4());
    }
    
    public Flux<String> zipWith(){
        
        Flux<String> one = Flux.just("A","B","C");
        
        Flux<String> two = Flux.just("D","E","F");
        
        return one.zipWith(two, (a,b) -> a+b );
    }
    
    public Mono<String> zipWithOnMono(){
        
        Mono<String> one = Mono.just("A");
        Mono<String> two = Mono.just("D");
        
        return one.zipWith(two, (a,b) -> a+b);
//        return one.zipWith(two).map(t->t.getT1()+t.getT2());
    }
}
