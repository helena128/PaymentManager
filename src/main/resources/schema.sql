DROP TABLE IF EXISTS payments;
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    cardholder_name VARCHAR(255),
    card_number VARCHAR(255) NOT NULL,
    expiry_date DATE NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    sum DECIMAL NOT NULL,
    created_datetime TIMESTAMPTZ);