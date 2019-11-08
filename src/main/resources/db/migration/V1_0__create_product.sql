CREATE TABLE IF NOT EXISTS product (
    prod_id INT AUTO_INCREMENT,
    prod_name VARCHAR(200) NOT NULL,
    prod_description VARCHAR(500) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    constraint order_pk primary key (prod_id)
);
