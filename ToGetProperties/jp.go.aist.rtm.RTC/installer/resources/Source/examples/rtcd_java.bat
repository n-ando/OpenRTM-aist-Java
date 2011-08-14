@echo off

set CLASSPATH=.;%RTM_JAVA_ROOT%\jar\OpenRTM-aist-1.0.0.jar;%RTM_JAVA_ROOT%\jar\commons-cli-1.1.jar;%RTM_JAVA_ROOT%\jar\rtcd.jar;
rem java -jar "%RTM_JAVA_ROOT%\jar\rtcd.jar" -f "%RTM_JAVA_ROOT%\bin\rtcd_java.conf" %*
java rtcd.rtcd  -f "%RTM_JAVA_ROOT%\bin\rtcd_java.conf" %*

