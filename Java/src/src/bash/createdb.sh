#!/bin/bash

# Look for database name passed in from the command line,
# or use "alm" as the default
db_name=alm
if [ $1 ] ; then
  echo "Using $1 for db_name"
  db_name=$1
fi

echo "Creating database $db_name"

mysql -v -u user -ppassword << EOF
CREATE DATABASE $db_name;
USE $db_name;

CREATE TABLE employees (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20),
  password VARCHAR(20)
);

CREATE TABLE fares (
  id INT PRIMARY KEY AUTO_INCREMENT,
  emp_id INT,
  pickup VARCHAR(50) COMMENT 'Street address',
  dropoff VARCHAR(50) COMMENT 'Street address',
  start DATETIME,
  end DATETIME,
  fare_charge INT COMMENT 'USD in pennies',
  driver_fee INT COMMENT 'USD in pennies',
  passenger_rating TINYINT UNSIGNED COMMENT 'From 0 to 5',
  driver_rating TINYINT UNSIGNED COMMENT 'From 0 to 5',
  FOREIGN KEY (emp_id) references employees(id)
);

INSERT INTO employees (username, password) VALUES ('fred','fredpassword');
INSERT INTO employees (username, password) VALUES ('barney','barneypassword');
INSERT INTO employees (username, password) VALUES ('wilma','wilmapassword');
INSERT INTO employees (username, password) VALUES ('betty','bettypassword');
EOF
