package com.studies.reactivereactor.b.operators.transform;

import java.util.List;
import java.util.function.Function;

import reactor.core.publisher.Flux;

public class TransformOperator {

    
    public Flux<String> transformOperator(int length) {

        Function<Flux<String>, Flux<String>> function = value -> value.map(String::toUpperCase)
                                                                .filter(string -> string.length() > length);
        
        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .transform(function)
                .map(value -> String.format("%s-%s", value.length(), value));
    }
}
