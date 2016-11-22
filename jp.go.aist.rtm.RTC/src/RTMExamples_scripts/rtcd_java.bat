@echo off

call set_classpath.bat
set CLASSPATH=%CLASSPATH%;%RTM_JAVA_ROOT%\jar\rtcd.jar;
rem java -jar "%RTM_JAVA_ROOT%\jar\rtcd.jar" -f "%RTM_JAVA_ROOT%\bin\rtcd_java.conf" %*
java rtcd.rtcd  -f "%RTM_JAVA_ROOT%\bin\rtcd_java.conf" %*

