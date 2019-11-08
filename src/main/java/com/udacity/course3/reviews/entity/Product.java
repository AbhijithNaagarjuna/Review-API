package com.udacity.course3.reviews.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prod_id")
    private Integer prodId;

    @Column(name="prod_name", nullable = false)
    private String prodName;

    @Column(name="prod_description", nullable = false)
    private String prodDescription;

    @Column(name="created_time")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList;

    public Product() {

    }

    public Product(String prodName, String prodDescription) {
        this.prodName = prodName;
        this.prodDescription = prodDescription;
    }

    public Integer getProdId() {
        return prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}

/*

CREATE TABLE IF NOT EXISTS product (
    prod_id INT AUTO_INCREMENT,
    prod_name VARCHAR(200) NOT NULL,
    prod_description VARCHAR(500) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    constraint order_pk primary key (prod_id)
);

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
 */