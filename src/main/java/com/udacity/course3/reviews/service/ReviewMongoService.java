package com.udacity.course3.reviews.service;

public interface ReviewMongoService {
    public void saveReview(com.udacity.course3.reviews.entity.Review jpaReview);

    public com.udacity.course3.reviews.model.Review retrieveReviewById(Integer reviewId);
}
