

For /R .\ %%a IN (*build.bat) do (
@echo "%%a"
@echo "%%~da%%~pa"
cd "%%~da%%~pa"
%%a)