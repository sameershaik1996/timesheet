
set tag=%1
echo %tag%
For /R .\ %%a IN (*build.bat) do (

@echo "%%~da%%~pa"
cd "%%~da%%~pa"
%%a %tag%)