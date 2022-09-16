package com.studies.wscarreview.exception;

public class CarReviewNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 3735935039118769771L;

    public CarReviewNotFoundException(String message) {
        super(message);
    }
}
