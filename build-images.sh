#!/bin/bash

echo "Performing run build for React manager"
# shellcheck disable=SC2164
cd ./react-manager
echo "Building React manager"
docker build --tag react-manager .

echo "Performing a clean Maven build for spring-react"
#shellcheck disable=SC2164
cd ../spring-manager
./mvnw clean package -DskipTests=true

echo "Building spring-manager service"
docker build --tag spring-manager .
