package com.studies.reactivereactor.c.combining.mergesequential;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class MergeSequential {

    
    public Flux<String> mergeSequential(){
        
        Flux<String> one = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        
        Flux<String> two = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(150));;
        
        return Flux.mergeSequential(one, two);
    }
}
