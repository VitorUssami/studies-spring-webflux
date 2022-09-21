package com.studies.reactivereactor.a.first.mono;

import reactor.core.publisher.Mono;

public class FirstMono {

    public Mono<String> nameMono() {

        return Mono.just("A").log();
    }

    public static void main(String[] args) {
        FirstMono firstMono = new FirstMono();
        firstMono
            .nameMono()
            .subscribe(name -> System.out.println(name));
    }

}
