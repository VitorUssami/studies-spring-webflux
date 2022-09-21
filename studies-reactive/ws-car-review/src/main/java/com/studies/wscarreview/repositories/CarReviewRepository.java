package com.studies.wscarreview.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.studies.wscarreview.domain.CarReview;

import reactor.core.publisher.Flux;

public interface CarReviewRepository extends ReactiveMongoRepository<CarReview, String>{

    Flux<CarReview> findByCarInfoId(String carInfoId);
}
