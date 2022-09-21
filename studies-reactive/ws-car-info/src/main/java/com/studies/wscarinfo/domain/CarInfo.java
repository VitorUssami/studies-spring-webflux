package com.studies.wscarinfo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Document
public class CarInfo {

    @Id
    private String carInfoId;
    private String model;
    private String modelDescription;
    private String color;
    private Double price;
    private Integer modelDate;
    
}
