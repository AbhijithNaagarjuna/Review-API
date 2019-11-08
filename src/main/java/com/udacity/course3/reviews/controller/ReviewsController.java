package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.service.ReviewMongoService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format."),
        @ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized. "),
        @ApiResponse(code = 500, message = "The server is down. Please make sure that the Location microservice is running.")
})
public class ReviewsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMongoService reviewMongoService;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId,
                                                    @RequestBody Review review ) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            review.setProduct(product);
            Review createdReview = reviewRepository.save(review);

            reviewMongoService.saveReview(createdReview);

            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            /*
                When loading reviews for a product,
                the service reads the ids from MySQL and the review document from MongoDB.
             */
            List<Review> allJpaReviews = reviewRepository.findAllByProduct(product);
            List<com.udacity.course3.reviews.model.Review> allMongoReviews = new ArrayList<>();

            for (Review review : allJpaReviews) {
                com.udacity.course3.reviews.model.Review mongoReview =
                        reviewMongoService.retrieveReviewById(review.getReviewId());
                allMongoReviews.add(mongoReview);
            }
            return new ResponseEntity<>(allMongoReviews, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
    }
}