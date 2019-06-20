

set APPLICATION_NAME=zuul
set IMAGE_NAME=pss_%APPLICATION_NAME%
set REGISTERGY_NAME=ssameer03
set REPO_IMAGE=%REGISTERGY_NAME%/%IMAGE_NAME%:beta
echo "> Cleaning target..."

echo "> building jar..."

call mvn package -Dmaven.test.skip=true

echo "> Building %APPLICATION_NAME%:latest"
docker login -u ssameer03 -pSameer@123
docker build -t %IMAGE_NAME%:beta .

docker tag %IMAGE_NAME% %REPO_IMAGE%

docker push %REPO_IMAGE%
echo "> Done."

