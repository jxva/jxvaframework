set DERBY_INSTALL=C:\Program Files\Sun\JavaDB
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_10
set CLASSPATH=%CLASSPATH%;%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbytools.jar;%DERBY_INSTALL%\lib\derbynet.jar;
cd %DERBY_INSTALL%\bin
startNetworkServer.bat
