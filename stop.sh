#!/bin/bash

echo "Stopping all microservices..."

pkill -f "mvn spring-boot:run"

echo "All services stopped."