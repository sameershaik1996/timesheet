
call newman run "C:\Users\redshift\Desktop\TimeSheet.postman_collection" -e "C:\Users\redshift\Desktop\test.postman_environment.json"


docker stop employeeService database authService apiGateway serviceDiscovery timesheetService
docker rm employeeService database authService apiGateway serviceDiscovery timesheetService

build-images.bat latest
