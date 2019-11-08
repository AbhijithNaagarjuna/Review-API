package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewMongoRepositoryTest {
    @Autowired
    private ReviewMongoRepository reviewMongoRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(reviewMongoRepository).isNotNull();
    }

    @Test
    public void testCreateReview(){
        Review review = new Review();
        review.setReviewId(1234);
        review.setReviewTitle("The best $700 iPhone Apple has ever made");
        review.setReviewText("Even faster speed, improved battery life.");
        review.setRecommended(true);

        reviewMongoRepository.save(review);

        Review fromDb = reviewMongoRepository.findById(review.getReviewId()).orElse(null);
        assertThat(fromDb.getReviewId()).isEqualTo(review.getReviewId());

        reviewMongoRepository.delete(review); // clean up
    }

    @Test
    public void testFindAll(){
        Review review = new Review();
        review.setReviewId(1234);
        review.setReviewTitle("The best $700 iPhone Apple has ever made");
        review.setReviewText("Even faster speed, improved battery life.");
        review.setRecommended(true);

        reviewMongoRepository.save(review);

        List<Review> allReviews = reviewMongoRepository.findAll();
        assertThat(allReviews).hasSize(1).extracting(Review::getReviewTitle)
                .containsOnly(review.getReviewTitle());

        reviewMongoRepository.delete(review); // clean up
    }
}
