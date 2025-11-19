#!/bin/bash

BASE="/Users/saitejanedunoori/Desktop/Projects/FitnessApp"

# Directories
EUREKA_DIR="$BASE/eureka"
AISERVICE_DIR="$BASE/AIRecommendationService"
ACTIVITY_DIR="$BASE/activityservice"
USER_DIR="$BASE/userservice"

# Ports
EUREKA_PORT=8761
AISERVICE_PORT=8083
ACTIVITY_PORT=8082
USER_PORT=8081

# Eureka URL
EUREKA_URL="http://localhost:$EUREKA_PORT/eureka/apps"

EUREKA_UP=0

#############################
# COMMON FUNCTIONS
#############################

kill_port() {
    PID=$(lsof -ti :$1)
    if [ -n "$PID" ]; then
        echo "Killing process on port $1 (PID: $PID)"
        kill -9 $PID
    fi
}

check_eureka_up() {
    if curl -s --max-time 1 --head "$EUREKA_URL" | grep "200" > /dev/null; then
        EUREKA_UP=1
    else
        EUREKA_UP=0
    fi
}

#############################
# START FUNCTIONS
#############################

start_eureka() {
    kill_port $EUREKA_PORT
    echo "Starting Eureka..."
    cd "$EUREKA_DIR"
    mvn spring-boot:run &
    echo "Eureka started."
}

start_microservices() {
    echo "Starting aiservice..."
    kill_port $AISERVICE_PORT
    cd "$AISERVICE_DIR"
    mvn spring-boot:run &

    echo "Starting activityservice..."
    kill_port $ACTIVITY_PORT
    cd "$ACTIVITY_DIR"
    mvn spring-boot:run &

    echo "Starting userservice..."
    kill_port $USER_PORT
    cd "$USER_DIR"
    mvn spring-boot:run &
}

run_all() {
    start_eureka

    echo "Checking Eureka health..."
    sleep 3
    check_eureka_up

    if [ "$EUREKA_UP" -eq 1 ]; then
        echo "Eureka is UP. Starting other services..."
        start_microservices
    else
        echo "❌ Eureka DOWN. Not starting microservices."
    fi
}

#############################
# STOP FUNCTIONS
#############################

stop_service() {
    case $1 in
        eureka) kill_port $EUREKA_PORT ;;
        aiservice) kill_port $AISERVICE_PORT ;;
        activityservice) kill_port $ACTIVITY_PORT ;;
        userservice) kill_port $USER_PORT ;;
    esac
    echo "$1 stopped."
}

stop_all() {
    stop_service eureka
    stop_service aiservice
    stop_service activityservice
    stop_service userservice
    echo "All services stopped."
}

#############################
# STATUS FUNCTIONS
#############################

check_status() {
    local NAME=$1
    local PORT=$2

    PID=$(lsof -ti :$PORT)
    if [ -n "$PID" ]; then
        echo "$NAME ➜ RUNNING"
    else
        echo "$NAME ➜ STOPPED"
    fi
}

status_all() {
    check_status "Eureka" $EUREKA_PORT
    check_status "aiservice" $AISERVICE_PORT
    check_status "activityservice" $ACTIVITY_PORT
    check_status "userservice" $USER_PORT
}

#############################
# COMMAND HANDLER
#############################

case $1 in

    start)
        run_all
        ;;

    stop)
        if [ -n "$2" ]; then
            stop_service "$2"
        else
            stop_all
        fi
        ;;

    status)
        status_all
        ;;

    *)
        echo "Usage:"
        echo "./services.sh start"
        echo "./services.sh stop"
        echo "./services.sh stop <service>"
        echo "./services.sh status"
        ;;
esac