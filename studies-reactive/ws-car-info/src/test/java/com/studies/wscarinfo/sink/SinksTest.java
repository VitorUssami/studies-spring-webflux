package com.studies.wscarinfo.sink;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

public class SinksTest {

    
    @Test
    public void simpleTest() {
        
        Many<Object> replayAll = Sinks.many().replay().all(); //subscribers receives all events, even the olds
        
        
        replayAll.emitNext(1,  Sinks.EmitFailureHandler.FAIL_FAST);
        replayAll.emitNext(2,  Sinks.EmitFailureHandler.FAIL_FAST);
        
        replayAll.asFlux()
                .subscribe(evt -> System.out.println("sub1 "+ evt));
        replayAll.asFlux()
                .subscribe(evt -> System.out.println("sub2 "+ evt));
        
        replayAll.tryEmitNext(3);
        
        replayAll.asFlux().subscribe(evt -> System.out.println("sub3 "+ evt));
    }
    
    @Test
    public void latestTest() {
        
        Many<Object> replayAll = Sinks.many().replay().latest();
        
        
        replayAll.emitNext(1,  Sinks.EmitFailureHandler.FAIL_FAST);
        replayAll.asFlux()
                .subscribe(evt -> System.out.println("sub1 "+ evt));
        
        replayAll.emitNext(2,  Sinks.EmitFailureHandler.FAIL_FAST);
        replayAll.asFlux()
                .subscribe(evt -> System.out.println("sub2 "+ evt));
        
        replayAll.tryEmitNext(3);
        replayAll.tryEmitNext(4);
        
        replayAll.asFlux()
                .subscribe(evt -> System.out.println("sub3 "+ evt));
    }
    
    @Test
    public void multicastTest() {
        
        Many<Object> multicast = Sinks.many().multicast().onBackpressureBuffer(); //subscribers receives all events published after the subscription
                
        multicast.emitNext(1,  Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(2,  Sinks.EmitFailureHandler.FAIL_FAST);
        
        multicast.asFlux()
                .subscribe(evt -> System.out.println("sub1 " + evt));
        multicast.asFlux()
                .subscribe(evt -> System.out.println("sub2 "+ evt));
        
        multicast.emitNext(3,  Sinks.EmitFailureHandler.FAIL_FAST);
        
//        multicast.asFlux().subscribe(evt -> System.out.println("sub3 "+ evt));
//        multicast.emitNext(4,  Sinks.EmitFailureHandler.FAIL_FAST);
    }
    
    @Test
    public void unicastTest() {
        
        Many<Object> multicast = Sinks.many().unicast().onBackpressureBuffer();
        
        multicast.emitNext(1,  Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(2,  Sinks.EmitFailureHandler.FAIL_FAST);
        
        multicast.asFlux()
                .subscribe(evt -> System.out.println("sub1 "+ evt));
//        multicast.asFlux().subscribe(evt -> System.out.println("sub2 "+ evt)); 
        //unicast() allows only one subscriber. 
        //If there are more than one the result will be: java.lang.IllegalStateException: UnicastProcessor allows only a single Subscriber
        
        multicast.emitNext(3,  Sinks.EmitFailureHandler.FAIL_FAST);
    }
    
    @Test
    void onBackpressureBufferTest() throws InterruptedException {

        Sinks.Many<Integer> multicast = Sinks.many().multicast().onBackpressureBuffer(); //by default holds up to 256 elements
//        Sinks.Many<Integer> multicast = Sinks.many().multicast().onBackpressureBuffer(500);

        IntStream.rangeClosed(0,260).forEach(multicast::tryEmitNext);
        multicast.tryEmitNext(261);

        multicast.asFlux()
            .subscribe(evt -> System.out.println("sub1 "+ evt));

        multicast.tryEmitNext(262);

        multicast.asFlux()
            .subscribe(evt -> System.out.println("sub1 "+ evt));

        multicast.tryEmitNext(263);
    }
}
