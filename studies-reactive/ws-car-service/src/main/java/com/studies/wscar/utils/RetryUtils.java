package com.studies.wscar.utils;

import java.time.Duration;

import com.studies.wscar.clients.CarInfoRestClient.CarInfoServerException;
import com.studies.wscar.clients.CarReviewsRestClient.CarReviewsServerException;

import reactor.core.Exceptions;
import reactor.util.retry.Retry;

public class RetryUtils {

    public static Retry retrySpec() {
        return Retry.fixedDelay(3 , Duration.ofSeconds(1))
                .filter(exception -> exception instanceof CarInfoServerException || exception instanceof CarReviewsServerException)
                .onRetryExhaustedThrow((backoffSpec, signal) -> Exceptions.propagate(signal.failure()));
    }
}
