package com.studies.wscarinfo.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CarInfoDTO implements Serializable{
    
    private static final long serialVersionUID = 4335061779539652040L;

    private String carInfoId;
    
    @NotBlank
    private String model;
    
    private String modelDescription;
    
    private String color;
    
    @NotNull
    private Double price;
    
    @NotNull
    @Min(value = 1886)
    private Integer modelDate;
    
}
