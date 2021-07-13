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
    isOfficeHours BOOLEAN,
    rideSharing BOOLEAN,
    averageSpeed DOUBLE
    );

insert into price_Calculation(
    sourceId,
    sourceName,
    distanceFromOrigin,
    sourceArea,
    isOfficeHours,
    rideSharing,
    averageSpeed
    )
VALUES
(1, 'Halifax', 35, 'urban',true,false,25),
(2, 'Dartmouth', 30, 'rural', false, true, 28),
(3, 'BedFord', -8, 'urban', false, true , 26),
(4, 'Sydney', 18, 'urban', true, false, 24),
(5, 'Yarmouth', 23, 'rural', false, false, 27),
(6, 'Toronto', 148, 'urban', true, true, 30),
(7, 'Kentville', -64, 'rural', true, false, 22),
(8, 'Winnipeg', 80, 'rural', false, true, 31),
(9, 'Vancouver', 260, 'urban', true, false, 34),
(10, 'Montreal', -190, 'rural', false, false, 29);


CREATE TABLE IF NOT EXISTS cab_Selection (
    cabId INT AUTO_INCREMENT PRIMARY KEY,
    cabName VARCHAR(45) NOT NULL,
    distanceFromOrigin DOUBLE,
    trafficDensity VARCHAR(30),
    genderPreference VARCHAR (10),
    FOREIGN KEY (driver_id) REFERENCES driver_ratings(driver_id),
    FOREIGN KEY (rating) REFERENCES driver_ratings(rating),
    PRIMARY KEY (cabId)
    );