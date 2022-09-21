package com.studies.wscarinfo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.studies.wscarinfo.domain.CarInfo;

public interface CarInfoRepository extends ReactiveMongoRepository<CarInfo, String> {

}
