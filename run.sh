#!/bin/bash

# Step 1: Run docker-compose down to remove containers, networks, and images
echo "Running docker-compose down..."
docker-compose down --rmi=all

# Step 2: Stop and remove all base-station containers
echo "Stopping and removing all base-station containers..."
docker stop $(docker ps -q --filter "name=^base-station")
docker rm $(docker ps -aq --filter "name=^base-station")

# Step 3: Clean and install with Maven
echo "Running Maven clean install..."
mvn clean install

# Step 4: Run the Java application (Make sure to replace 'whatever' with the actual JAR file name)
# Dynamically pick the latest .jar file, excluding the .original
JAR_FILE=$(ls container-manager/target/*.jar | grep -v '.original' | sort -t- -k2,2 -k3,3 -k4,4 | tail -n 1)

# Run the Java application with the selected JAR file
echo "Running Java application from $JAR_FILE..."
java -jar $JAR_FILE &

# Step 5: Bring up the Docker containers with docker-compose
echo "Starting docker containers with docker-compose up..."
docker-compose up --build -d

# Leave terminal open
echo "Process completed! You can now continue using the terminal."

