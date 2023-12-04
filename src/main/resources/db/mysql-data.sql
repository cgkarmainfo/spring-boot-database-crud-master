
-- Insert sample data into Product table
INSERT INTO product (name, price, status, posted_date, approval_id) VALUES
    ('Product A', 5000.00, 'ACTIVE', '2023-01-01 12:00:00', NULL),
    ('Product B', 8000.00, 'ACTIVE', '2023-01-02 10:30:00', NULL),
    ('Product C', 12000.00, 'ACTIVE', '2023-01-03 14:45:00', NULL);

-- Insert sample data into Approval Queue table
INSERT INTO approval_queue (product_id, request_date) VALUES
    (1, '2023-01-01 12:30:00'),
    (2, '2023-01-02 11:00:00');