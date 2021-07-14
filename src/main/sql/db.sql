CREATE DATABASE IF NOT EXISTS cabby;

USE cabby;

CREATE TABLE IF NOT EXISTS admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS driver (
    driver_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    status BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

insert into driver(
    driver_id,
    name,
    email,
    password,
    username,
    status
)
VALUES
    (11, 'Devraj','devraj@gmail.com','devraj@123','devraj',true),
    (12,'Manjinder','manjinder@gmail.com','manjinder@123','manjinder',true),
    (13,'Bikram','bikram@gmail.com','bikram@123','bikram',true),
    (14,'Arvinder','arvinder@gmail.com','arvinder@123','arvinder',true),
    (15,'Manraj','manraj@gmail.com','manraj@123','manraj',true);

CREATE TABLE IF NOT EXISTS customer (
    cust_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    status BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS places (
    place_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_place_servicable BOOLEAN,
    distance_from_origin INT
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    driver_id INT,
    cust_id INT,
    travel_time VARCHAR(30),
    estimated_price DOUBLE,
    source VARCHAR(255),
    destination VARCHAR(255),
    is_trip_done BOOLEAN DEFAULT false,
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id),
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id)
);

CREATE TABLE IF NOT EXISTS trips (
    trip_id INT AUTO_INCREMENT PRIMARY KEY,
    driver_id INT,
    cust_id INT,
    booking_id INT,
    trip_amount DOUBLE,
    distance_covered DOUBLE,
    trip_start_time VARCHAR(30),
    trip_end_time VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id),
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
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

CREATE TABLE IF NOT EXISTS price_Calculation (
    sourceId INT AUTO_INCREMENT PRIMARY KEY,
    sourceName VARCHAR(45) NOT NULL,
    distanceFromOrigin DOUBLE,
    sourceArea VARCHAR(45),
    rideSharing BOOLEAN,
    averageSpeed DOUBLE
    );

insert into price_Calculation(
    sourceId,
    sourceName,
    distanceFromOrigin,
    sourceArea,
    rideSharing,
    averageSpeed
    )
VALUES
    (1, 'Halifax', 35, 'urban', false,25),
    (2, 'Dartmouth', 30, 'rural', true, 28),
    (3, 'BedFord', -8, 'urban', true , 26),
    (4, 'Sydney', 18, 'urban', false, 24),
    (5, 'Yarmouth', 23, 'rural', false, 27),
    (6, 'Toronto', 148, 'urban', true, 30),
    (7, 'Kentville', -64, 'rural', false, 22),
    (8, 'Winnipeg', 80, 'rural', true, 31),
    (9, 'Vancouver', 260, 'urban', false, 34),
    (10, 'Montreal', -190, 'rural', false, 29);


CREATE TABLE IF NOT EXISTS cabs(
    cabId INT AUTO_INCREMENT PRIMARY KEY,
    cabName VARCHAR(45) NOT NULL,
    cabDistanceFromOrigin DOUBLE,
    trafficDensity VARCHAR(30),
    genderPreference VARCHAR (10),
    driver_id INT,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id)
    );

insert into cabs(
    cabId,
    cabName,
    cabDistanceFromOrigin,
    trafficDensity,
    genderPreference,
    driver_id
)
VALUES
    (101, 'Cab1', 32, 'low', true, 11),
    (102, 'Cab2', 33, 'moderate', false, 12),
    (103, 'Cab3', 37, 'high', true, 13),
    (104, 'Cab4', 38, 'low', false, 14),
    (105, 'Cab5', 42, 'high', false, 15);