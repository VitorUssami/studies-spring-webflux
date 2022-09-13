package com.studies.reactive_reactor.b.operators.concatmap;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import reactor.core.publisher.Flux;

public class ConcatMapOperator {

    public Flux<String> concatMap() {

        return Flux.fromIterable(List.of("a", "bb", "ccc"))
                .map(String::toUpperCase)
                .concatMap(this::splitWithDelay)
                .log();
    }

    private Flux<String> splitWithDelay(String string) {

        int nextInt = new Random().nextInt(1000);
        return Flux.fromArray(string.split(""))
                    .delayElements(Duration.ofMillis(nextInt));
    }

}
