package com.studies.reactivereactor.a.first.flux;

import java.util.List;

import reactor.core.publisher.Flux;

public class FirstFlux {

    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("A","B","C")).log();
    }
    
    
    public static void main(String[] args) {
        FirstFlux firstFlux = new FirstFlux(); 
        firstFlux
            .namesFlux()
//            .subscribe(name -> System.out.println(name));
            .subscribe(System.out::println);
    }
}
