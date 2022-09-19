package com.studies.wscar.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private CarInfoDTO info;
    private List<CarReviewDTO> reviews;
}
