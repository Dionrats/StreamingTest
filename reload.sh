#!/usr/bin/env bash

clear
docker-compose down
mvn clean package dockerfile:build
docker-compose up -d

#clear
#docker attach  streamingtest_serviceProducer_1
