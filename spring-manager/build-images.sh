#!/bin/bash

# shellcheck disable=SC2164
cd ../spring-manager
./mvnw clean package -DskipTests=true

echo "Building spring-manager service"
docker build --tag spring-manager .
