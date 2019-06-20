@echo off 

set build =%1

IF "%build%"=="zuul" (
    echo %build%
    goto zuul)
IF "%build%"=="eureka" goto eureka
IF "%build%"=="auth" goto auth
IF "%build%"=="employee" goto employee
IF "%build%"=="timesheet" goto timesheet


:zuul
set arg1="timesheet"
set arg2="pss_timesheet"
set arg3="timesheetService"
goto exec


:eureka
set arg1="eureka"
set arg2="pss_eureka"
set arg3="serviceDiscovery"
goto exec

:auth
set arg1="auth"
set arg2="pss_auth"
set arg3="authService"
goto exec

:employee
set arg1="employee"
set arg2="pss_employee"
set arg3="employeeService"
goto exec

:timesheet
set arg1="timesheet"
set arg2="pss_timesheet"
set arg3="timesheetService"
goto exec

:database
docker-compose up database
exit

:all
docker-compose up
exit



:exec
echo %arg1% %arg2% %arg3%

cd %arg1%

call mvn   package -Dmaven.test.skip=true

docker  build  -t %arg2% .

cd ../

docker-compose up %arg3%

