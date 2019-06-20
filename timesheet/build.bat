

set APPLICATION_NAME=timesheet
set IMAGE_NAME=pss_%APPLICATION_NAME%
set REGISTERGY_NAME=ssameer03
set REPO_IMAGE=%REGISTERGY_NAME%/%IMAGE_NAME%:beta
echo "> Cleaning target..."
rm -Rf target
echo "> building jar..."
call mvn install:install-file -Dfile="native jars/filter-0.0.1-SNAPSHOT.jar.original"  -DgroupId=us.redshift -DartifactId=filter -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -e
call mvn package -Dmaven.test.skip=true

echo "> Building %APPLICATION_NAME%:latest"
docker login -u ssameer03 -pSameer@123
docker build -t %IMAGE_NAME%:beta .

docker tag %IMAGE_NAME% %REPO_IMAGE%

docker push %REPO_IMAGE%
echo "> Done."

