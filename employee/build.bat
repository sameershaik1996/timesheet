

set APPLICATION_NAME=employee
set IMAGE_NAME=pss_%APPLICATION_NAME%
set REGISTERGY_NAME=ssameer03
set REPO_IMAGE=%REGISTERGY_NAME%/%IMAGE_NAME%:latest
echo "> Cleaning target..."
rm -Rf target
echo "> building jar..."
call mvn package -Dmaven.test.skip=true

echo "> Building $APPLICATION_NAME:latest"
docker build -t %IMAGE_NAME%:latest .

docker tag pss_employee %REPO_IMAGE%

docker push %REPO_IMAGE%
echo "> Done."

