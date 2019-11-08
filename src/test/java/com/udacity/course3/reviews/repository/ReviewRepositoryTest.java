package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired private ReviewRepository reviewRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(testEntityManager).isNotNull();
        assertThat(reviewRepository).isNotNull();
    }

    @Test
    public void testCreateReviewForProduct(){
        Product product = new Product("iPhone 11",
                "Welcome to the big screens.  Super retina.  Even faster Face ID.");
        testEntityManager.persist(product);
        testEntityManager.flush();

        Review review = new Review("The best $700 iPhone Apple has ever made",
                "Even faster speed, improved battery life.", true);
        review.setProduct(product);
        reviewRepository.save(review);

        Review fromDb = reviewRepository.findById(review.getReviewId()).orElse(null);
        assertThat(fromDb.getReviewId()).isEqualTo(review.getReviewId());

        reviewRepository.delete(review); // clean up
    }

    @Test
    public void testFindAll(){
        Product product = new Product("iPhone 11",
                "Welcome to the big screens.  Super retina.  Even faster Face ID.");
        Review review1 = new Review("The best $700 iPhone Apple has ever made",
                "Even faster speed, improved battery life.", true);
        review1.setProduct(product);

        testEntityManager.persist(product);
        testEntityManager.persist(review1);
        testEntityManager.flush();

        List<Review> allReviews = reviewRepository.findAllByProduct(product);
        assertThat(allReviews).hasSize(1).extracting(Review::getReviewTitle)
                .containsOnly(review1.getReviewTitle());
    }
}


