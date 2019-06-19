#!/bin/bash

APPLICATION_NAME=employee

echo "> Cleaning target..."
rm -Rf target
echo "> building jar..."
mvn package -Dmaven.test.skip=true

echo "> Building $APPLICATION_NAME:latest"
docker build -t $APPLICATION_NAME:latest .

echo "> Done."

