CREATE DATABASE IF NOT EXISTS cabby;

USE cabby;

CREATE TABLE IF NOT EXISTS cabby_admin (
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
    (15,'Manraj','manraj@gmail.com','manraj@123','manraj',true),
    (16,'Dummy1','dummy1@gmail.com','dummy@123','dummy1',true),
    (17,'Dummy2','dummy2@gmail.com','dummmy@123','dummy2',true);

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
    (2, 'Dartmouth', 57, 'rural', true, 28),
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
    routeTrafficDensity VARCHAR(30),
    cabSpeedOnRoute DOUBLE,
    genderPreference VARCHAR (10),
    driver_id INT,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id)
    );

insert into cabs(
    cabId,
    cabName,
    cabDistanceFromOrigin,
    routeTrafficDensity,
    cabSpeedOnRoute,
    genderPreference,
    driver_id
)
VALUES
    (101, 'Cab1', 24, 'high',30, false, 11),
    (102, 'Cab2', 29, 'low', 50, true, 12),
    (103, 'Cab3', 31, 'moderate',40, false, 13),
    (104, 'Cab4', 56, 'moderate',40, true, 14),
    (105, 'Cab5', 39, 'high',30, true, 15),
    (106, 'Cab6', 49, 'low',50, false, 16),
    (107, 'Cab7', 40, 'high',30, false, 17);

CREATE TABLE IF NOT EXISTS user_points (
    user_id int not null,
    user_type varchar(45) not null,
    total_points int
);

ALTER TABLE user_points add primary key (user_id, user_type);

insert into user_points values
(1, "DRIVER", 1000),
(1, "CUSTOMER", 2000),
(2, "DRIVER", 1500),
(2, "CUSTOMER", 2500);

CREATE TABLE IF NOT EXISTS coupons (
    coupon_id int auto_increment primary key,
    coupon_name varchar(45) not null,
    coupon_value double,
    price_in_points int
);

alter table coupons auto_increment = 100;

insert into coupons (coupon_name, coupon_value, price_in_points) values
("Walmart Shopping", 50.00, 500),
("Movie Ticket", 10.00, 100),
("Hair Cut At Saloon", 15.00, 150),
("Opera House Ticket", 100.00, 1000);

CREATE TABLE IF NOT EXISTS user_coupons (
    txn_id int auto_increment primary key,
    user_id int not null,
    user_type varchar(45) not null,
    coupon_id int not null
);
