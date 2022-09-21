package com.studies.reactivereactor.b.operators.empty;

import java.util.List;
import java.util.function.Function;

import reactor.core.publisher.Flux;

public class EmptyReturn {

    
    
    public Flux<String> defaultIfEmpty() {
        
        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .filter(string -> string.length() == -1)
                .defaultIfEmpty("default");
    }
    
    
    public Flux<String> switchIfEmpty() {

        Function<Flux<String>, Flux<String>> function = value -> value.map(String::toUpperCase)
                                                                .filter(string -> string.length() > 5);
        
        Flux<String> dafaultFlux = Flux.just("default").transform(function);
        
        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .transform(function)
                .switchIfEmpty(dafaultFlux)
                .map(value -> String.format("%s-%s", value.length(), value));
    }
}
