-- Create Product table schema
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    posted_date TIMESTAMP NOT NULL,
    approval_id BIGINT,
    FOREIGN KEY (approval_id) REFERENCES approval_queue(id)
);

-- Create Approval Queue table schema
CREATE TABLE approval_queue (
    id SERIAL PRIMARY KEY,
    product_id BIGINT,
    request_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
);