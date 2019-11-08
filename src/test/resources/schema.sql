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