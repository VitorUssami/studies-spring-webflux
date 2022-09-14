package com.studies.wscarinfo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studies.wscarinfo.domain.CarInfo;
import com.studies.wscarinfo.dto.CarDTO;
import com.studies.wscarinfo.repositories.CarInfoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarInfoService {

    
    private CarInfoRepository repo;
    private ModelMapper mapper;
    
    @Autowired
    public CarInfoService(CarInfoRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }


    public Mono<CarDTO> create(CarDTO car) {

        CarInfo carInfo = CarInfo.builder().build();
        mapper.map(car, carInfo);

        return repo.save(carInfo).log().map(this::entityToDTO);
    }


    public Flux<CarDTO> retrieve() {
        return repo.findAll().map(this::entityToDTO);
    }

    public Mono<CarDTO> retrieveById(String id) {
        return repo.findById(id).map(this::entityToDTO);
    }


    public Mono<CarDTO> update(String id, CarDTO car) {
        
        CarInfo carInfo = CarInfo.builder().build();
        mapper.map(car, carInfo);
        
        return repo.findById(id)
                .flatMap(carToUpdate -> {
                    carToUpdate.setModel(carInfo.getModel());
                    carToUpdate.setColor(carInfo.getColor());
                    carToUpdate.setModelDate(carInfo.getModelDate());
                    carToUpdate.setModelDescription(carInfo.getModelDescription());
                    carToUpdate.setPrice(carInfo.getPrice());
                    
                    return repo.save(carToUpdate);
                })
                .map(this::entityToDTO);
        
    }


    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }
    

    private CarDTO entityToDTO(CarInfo car) {
        CarDTO dto = CarDTO.builder().build();
        mapper.map(car, dto);
        return dto;
    }
}

