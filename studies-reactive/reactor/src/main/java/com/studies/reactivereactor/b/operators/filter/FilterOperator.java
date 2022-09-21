package com.studies.reactivereactor.b.operators.filter;

import java.util.List;

import reactor.core.publisher.Flux;

public class FilterOperator {
    
    public Flux<String> filterOperatorByLength(int length) {

        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .map(String::toUpperCase)
                .filter(value -> value.length() > length);
    }
    
    public Flux<String> filterOperatorByLengthThenTransform(int length) {

        return this.filterOperatorByLength(length)
                .map(value -> String.format("%s-%s", value.length(), value));
    }
}
