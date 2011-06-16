set DERBY_INSTALL=C:\Program Files\Sun\JavaDB
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_10
set CLASSPATH=%CLASSPATH%;%DERBY_INSTALL%\lib\derbyclient.jar;%DERBY_INSTALL%\lib\derbytools.jar;
cd %JAVA_HOME%\bin\
java org.apache.derby.tools.ij
connect 'jdbc:derby://127.0.0.1:1527/jxva;create=true';
