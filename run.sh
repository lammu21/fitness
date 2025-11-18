#!/bin/bash

echo "Starting Eureka..."
cd /Users/saitejanedunoori/Desktop/Projects/FitnessApp/eureka
mvn spring-boot:run &
PID_EUREKA=$!

sleep 5

echo "Starting AI Service..."
cd /Users/saitejanedunoori/Desktop/Projects/FitnessApp/aiservice
mvn spring-boot:run &
PID_AI=$!

echo "Starting Activity Service..."
cd /Users/saitejanedunoori/Desktop/Projects/FitnessApp/activityservice
mvn spring-boot:run &
PID_ACTIVITY=$!

echo "Starting User Service..."
cd /Users/saitejanedunoori/Desktop/Projects/FitnessApp/userservice
mvn spring-boot:run &
PID_USER=$!

echo ""
echo "All services started."
echo "Use ./stop-all.sh to stop them."
wait