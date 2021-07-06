CREATE DATABASE IF NOT EXISTS cabby;

CREATE TABLE IF NOT EXISTS driver (
    driver_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customer (
    cust_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS places (
    place_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_place_servicable BOOLEAN,
    distance_from_origin INT
);

CREATE TABLE IF NOT EXISTS trips (
    trip_id INT AUTO_INCREMENT PRIMARY KEY,
    driver_id INT,
    cust_id INT,
    driver_rating DOUBLE,
    customer_rating DOUBLE,
    trip_amount DOUBLE,
    distance_covered DOUBLE,
    trip_start_time TIMESTAMP,
    trip_end_time TIMESTAMP,
    source VARCHAR(255),
    destination VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_driver FOREIGN KEY (driver_id) REFERENCES driver(driver_id),
    CONSTRAINT fk_customer FOREIGN KEY (cust_id) REFERENCES customer(cust_id)
);

