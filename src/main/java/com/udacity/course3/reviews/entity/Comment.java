package com.udacity.course3.reviews.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "comment_title", nullable = false)
    private String commentTitle;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Comment() {}

    public Comment(String commentTitle, String commentText) {
        this.commentTitle = commentTitle;
        this.commentText = commentText;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public String getCommentText() {
        return commentText;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setReview(Review review) {
        this.review = review;
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