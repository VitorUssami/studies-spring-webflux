package com.studies.wscar.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarReviewDTO {

    private String carReviewId;
    
    @NotNull
    private String carInfoId;
    
    @NotNull
    private String comment;
    
    private Double rating;
    
}