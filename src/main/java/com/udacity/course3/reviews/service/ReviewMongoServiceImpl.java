package com.udacity.course3.reviews.service;

import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMongoServiceImpl implements ReviewMongoService {
    @Autowired
    private ReviewMongoRepository reviewMongoRepository;

    @Override
    public void saveReview(com.udacity.course3.reviews.entity.Review jpaReview) {
        com.udacity.course3.reviews.model.Review mongoReview =
                new com.udacity.course3.reviews.model.Review();
        mongoReview.setReviewId(jpaReview.getReviewId());
        mongoReview.setReviewTitle(jpaReview.getReviewTitle());
        mongoReview.setReviewText(jpaReview.getReviewText());
        mongoReview.setRecommended(jpaReview.isRecommended());
        reviewMongoRepository.save(mongoReview);
    }

    @Override
    public com.udacity.course3.reviews.model.Review retrieveReviewById(Integer reviewId) {
        com.udacity.course3.reviews.model.Review mongoReview = reviewMongoRepository.findById(reviewId)
                .orElse(null);
        return mongoReview;
    }
}
