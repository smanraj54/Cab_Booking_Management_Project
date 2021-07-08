#!/bin/bash

mvn clean install
echo "Starting the application"

java -jar target/cabby-1.0-jar-with-dependencies.jar
