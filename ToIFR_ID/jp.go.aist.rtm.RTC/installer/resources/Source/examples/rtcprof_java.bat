@echo off

set _ext_=%~x1

if "%_ext_%"==".jar" goto jar
set _cpath_=%~dp1
goto exec

:jar
set _cpath_=%~f1

:exec
set CLASSPATH=.;%RTM_JAVA_ROOT%\jar\OpenRTM-aist-1.0.0.jar;%RTM_JAVA_ROOT%\jar\commons-cli-1.1.jar;%RTM_JAVA_ROOT%\jar\rtcprof.jar;%_cpath_%
java -cp "%CLASSPATH%;" rtcprof.rtcprof %1

