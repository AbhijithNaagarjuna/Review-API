package com.udacity.course3.reviews.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "recommended")
    private boolean recommended;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name="prod_id")
    private Product product;

    public Review() {}

    public Review(String reviewTitle, String reviewText, boolean recommended) {
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.recommended = recommended;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}


/*
CREATE TABLE IF NOT EXISTS review (
    review_id INT AUTO_INCREMENT,
    review_title VARCHAR(200) NOT NULL,
    review_text  VARCHAR(500) NOT NULL,
    recommended  boolean,
    created_time TIMESTAMP NOT NULL,
    prod_id INT NOT NULL,
    constraint  review_pk primary key (review_id),
    constraint  review_product_fk
        foreign key (prod_id) references product (prod_id)
);

CREATE TABLE IF NOT EXISTS comment (
    comment_id INT AUTO_INCREMENT,
    comment_title VARCHAR(200) NOT NULL,
    comment_text  VARCHAR(500) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    review_id INT NOT NULL,
    constraint  comment_pk primary key (comment_id),
    constraint  comment_review_fk
        foreign key (review_id) references review (review_id)
);

 */