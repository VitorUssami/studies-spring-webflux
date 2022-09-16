package com.studies.wscarreview.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.studies.wscarreview.handlers.CarReviewsHandler;

@Configuration
public class CarReviewRouter {

    @Bean
    public RouterFunction<ServerResponse> reviewsRoute(CarReviewsHandler handler) {
        
        return RouterFunctions.route()
                .nest(RequestPredicates.path("/v1/car-reviews"), builder-> {
                    builder.POST("", request->handler.create(request))
                           .GET("", request->handler.retrieve(request))
                           .PUT("/{id}", request->handler.update(request))
                           .DELETE("/{id}", request->handler.delete(request));
                })
                .build();
    }
}
