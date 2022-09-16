package com.studies.wscarreview.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studies.wscarreview.domain.CarReview;
import com.studies.wscarreview.dto.CarReviewDTO;
import com.studies.wscarreview.exception.CarReviewDataException;
import com.studies.wscarreview.exception.CarReviewNotFoundException;
import com.studies.wscarreview.repositories.CarReviewRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarReviewService {

    private CarReviewRepository repo;
    private ModelMapper mapper;
    private Validator validator; 
    
    @Autowired
    public CarReviewService(CarReviewRepository repo, ModelMapper mapper, Validator validator) {
        this.repo = repo;
        this.mapper = mapper;
        this.validator = validator;
    }

    
    public Mono<CarReviewDTO> create(CarReviewDTO dto) {
        
        validateDto(dto);
        CarReview review = new CarReview();
        mapper.map(dto, review);
        Mono<CarReview> mono = repo.save(review).log();
        return mono.map(saved-> {
            mapper.map(saved, dto);
            return dto;
        });
    }

    public Flux<CarReviewDTO> retrieve(Optional<String> carInfoId) {
        
        Flux<CarReviewDTO> returnFlux; 
        
        if(carInfoId.isPresent()) {
            
            returnFlux = repo.findByCarInfoId(carInfoId.get())
                    .switchIfEmpty(Mono.error(new CarReviewNotFoundException("Not found: id "+carInfoId.get())))
                    .map(carReview-> {
                            CarReviewDTO dto = new CarReviewDTO();
                            mapper.map(carReview, dto);
                            return dto;
                        });
        }else {
            
            Flux<CarReview> findAll = repo.findAll();
            returnFlux = findAll.map(review->{
                CarReviewDTO dto = new CarReviewDTO();
                mapper.map(review, dto);
                return dto;
            });
        }
        return returnFlux;
    }

    public Mono<CarReviewDTO> update(String id, CarReviewDTO updatedDto) {
        
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new CarReviewNotFoundException("Not found: id "+id)))
                .flatMap(review-> {
                    review.setComment(updatedDto.getComment());
                    review.setRating(updatedDto.getRating());
                    return repo.save(review);
                })
                .map(saved -> {
                    CarReviewDTO dto = new CarReviewDTO();
                    mapper.map(saved, dto);
                    return dto;
                });
    }

    public Mono<Void> delete(String id) {
        
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new CarReviewNotFoundException("Not found: id "+id)))
                .flatMap(carReview->repo.delete(carReview));
    }
    
    private void validateDto(CarReviewDTO dto) {
        Set<ConstraintViolation<CarReviewDTO>> validate = validator.validate(dto);
        if (!validate.isEmpty()) {
            String message = validate.stream()
                    .map(err-> String.format("%s %s", err.getPropertyPath().toString(), err.getMessage()))
                    .collect(Collectors.joining(","));
            throw new CarReviewDataException(message);
        }
    }
}
