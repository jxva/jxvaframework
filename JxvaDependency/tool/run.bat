@echo off
set cp=
for %%i in (".\lib\*.jar") do call setenv.bat %%i
SET %cp%;
java -classpath %cp% com.jxva.tool.Toolkit