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

insert into cabby_admin(
    name,
    email,
    password,
    username
)
VALUES
    ('Admin1 Singh','admin1@gmail.com','admin1@123','admin1'),
    ('Admin2 Singh','admin2@gmail.com','admin2@123','admin2'),
    ('Admin3 Singh','admin3@gmail.com','admin3@123','admin3');

CREATE TABLE IF NOT EXISTS driver (
    driver_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    status BOOLEAN DEFAULT false,
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
    (1, 'Devraj','devraj@gmail.com','devraj@123','devraj',true),
    (2,'Manjinder','manjinder@gmail.com','manjinder@123','manjinder',false),
    (3,'Bikram','bikram@gmail.com','bikram@123','bikram',true),
    (4,'Arvinder','arvinder@gmail.com','arvinder@123','arvinder',false),
    (5,'Manraj','manraj@gmail.com','manraj@123','manraj',true),
    (6,'Driver1 Singh','driver1@gmail.com','driver1@123','driver1',true),
    (7,'Driver2 Singh','driver2@gmail.com','driver2@123','driver2',false),
    (8,'Driver3 Singh','driver3@gmail.com','driver3@123','driver3',true),
    (9,'Driver4 Singh','driver4@gmail.com','driver4@123','driver4',false),
    (10,'Driver5 Singh','driver5@gmail.com','driver5@123','driver5',true),
    (11,'Driver6 Singh','driver6@gmail.com','driver6@123','driver6',false ),
    (12,'Driver7 Singh','driver7@gmail.com','driver7@123','driver7',true),
    (13,'Driver8 Singh','driver8@gmail.com','driver8@123','driver8',false ),
    (14,'Driver9 Singh','driver9@gmail.com','driver9@123','driver9',true);

CREATE TABLE IF NOT EXISTS customer (
    cust_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    status BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

insert into customer(
    cust_id,
    name,
    email,
    password,
    username,
    status
)
VALUES
    (1, 'Customer1 Singh','cust1@gmail.com','cust1@123','cust1',true),
    (2,'Customer2 Singh','cust2@gmail.com','cust2@123','cust2',true),
    (3,'Customer3 Singh','cust3@gmail.com','cust3@123','cust3',true),
    (4,'Customer4 Singh','cust4@gmail.com','cust4@123','cust4',true),
    (5,'Customer5 Singh','cust5@gmail.com','cust5@123','cust5',true),
    (6,'Customer6 Singh','cust6@gmail.com','cust6@123','cust6',true),
    (7,'Customer7 Singh','cust7@gmail.com','cust7@123','cust7',true),
    (8,'Customer8 Singh','cust8@gmail.com','cust8@123','cust8',true),
    (9,'Customer9 Singh','cust9@gmail.com','cust9@123','cust9',true),
    (10,'Customer10 Singh','cust10@gmail.com','cust10@123','cust10',true);


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
    cab_id INT,
    travel_time VARCHAR(30),
    estimated_price DOUBLE,
    source VARCHAR(255),
    destination VARCHAR(255),
    is_cancelled BOOLEAN DEFAULT false,
    has_driver_cancelled BOOLEAN DEFAULT false,
    has_customer_cancelled BOOLEAN DEFAULT false,
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
    averageSpeed DOUBLE
    );

insert into price_Calculation(
    sourceId,
    sourceName,
    distanceFromOrigin,
    sourceArea,
    averageSpeed
)
VALUES
(1, 'Halifax', 35, 'urban', 25),
(2, 'Dartmouth', 57, 'rural', 28),
(3, 'BedFord', -8, 'urban', 26),
(4, 'Sydney', 18, 'urban', 24),
(5, 'Yarmouth', 23, 'rural', 27),
(6, 'Toronto', 148, 'urban', 30),
(7, 'Kentville', -64, 'rural', 22),
(8, 'Winnipeg', 80, 'rural', 31),
(9, 'Vancouver', 260, 'urban', 34),
(10, 'Montreal', -190, 'rural', 29);


CREATE TABLE IF NOT EXISTS cabs(
    cabId INT AUTO_INCREMENT PRIMARY KEY,
    cabName VARCHAR(45) NOT NULL,
    cabDistanceFromOrigin DOUBLE,
    routeTrafficDensity VARCHAR(30),
    cabSpeedOnRoute DOUBLE,
    driverGender VARCHAR(10),
    driver_id INT,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id)
    );

insert into cabs(
    cabId,
    cabName,
    cabDistanceFromOrigin,
    routeTrafficDensity,
    cabSpeedOnRoute,
    driverGender,
    driver_id
)
VALUES
(1, 'Cab1', 24, 'high',30,'Male', 1),
(2, 'Cab2', 30, 'low', 50,'Female', 2),
(3, 'Cab3', 31, 'moderate',40,'Male', 3),
(4, 'Cab4', 56, 'moderate',40,'Male', 4),
(5, 'Cab5', 39, 'high',30,'Female', 5),
(6, 'Cab6', 49, 'low',50,'Male', 6),
(7, 'Cab7', 40, 'high',30,'Male', 7),
(8, 'Cab8', 21, 'high',30,'Male', 8),
(9, 'Cab9', 13, 'low', 50,'Female', 9),
(10, 'Cab10', 59, 'moderate',40,'Male', 10),
(11, 'Cab11', 56, 'moderate',40,'Male', 11),
(12, 'Cab12', 61, 'high',30,'Female', 12),
(13, 'Cab13', 151, 'low',50,'Female', 13),
(14, 'Cab14', 146, 'high',30,'Male', 14);



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

insert into bookings(driver_id, cust_id, cab_id, travel_time, estimated_price, source, destination, is_trip_done)
values (1, 1, 1, "07/30/2021 12:30", 150.5, "Halifax", "Toronto", true),
        (2, 2, 2, "07/31/2021 12:30", 90.5, "BedFord", "Sydney", true),
        (3, 3, 3, "07/28/2021 12:30", 450.5, "Winnipeg", "Montreal", true);

insert into trips(driver_id, cust_id, booking_id, trip_amount, distance_covered, trip_start_time, trip_end_time)
values (1, 1, 1, 150.5, 1600, "07/30/2021 12:30", "07/30/2021 23:30"),
        (2, 2, 2, 90.5, 200, "07/31/2021 12:30", "07/31/2021 16:30"),
        (3, 3, 3, 450.5, 1200, "07/28/2021 12:30", "07/29/2021 08:30");

