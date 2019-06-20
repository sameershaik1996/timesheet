#!/bin/bash

APPLICATION_NAME=employee
set IMAGE_NAME=pss_%APPLICATION_NAME%
set REGISTERGY_NAME=ssameer03
set REPO_IMAGE=%REGISTERGY_NAME%/%IMAGE_NAME%:latest

echo "> Cleaning target..."
rm -Rf target
echo "> building jar..."
mvn package -Dmaven.test.skip=true

echo "> Building $APPLICATION_NAME:latest"
docker build -t $APPLICATION_NAME:latest .

echo "> Done."

