CREATE DATABASE IF NOT EXISTS cabby;

CREATE TABLE IF NOT EXISTS admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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

CREATE TABLE IF NOT EXISTS customer_ratings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cust_id int,
    trip_id int,
    rating int,
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id),
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);

CREATE TABLE IF NOT EXISTS driver_ratings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    driver_id int,
    trip_id int,
    rating int,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id),
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);

CREATE TABLE IF NOT EXISTS customer_score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cust_id int,
    trip_id int,
    score double,
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id),
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);

CREATE TABLE IF NOT EXISTS driver_score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    driver_id int,
    trip_id int,
    score double,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id),
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);