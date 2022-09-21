package com.studies.wscarreview.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class CarReview {

    @Id
    private String carReviewId;
    @NotNull
    private String carInfoId;
    private String comment;
    private Double rating;
}
