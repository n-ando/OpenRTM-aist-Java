set _cpath_=%~dp1

set CLASSPATH=.;%RTM_JAVA_ROOT%\jar\OpenRTM-aist-1.0.0.jar;%RTM_JAVA_ROOT%\jar\commons-cli-1.1.jar;%RTM_JAVA_ROOT%\jar\rtcprof.jar;%_cpath_%
rem java -jar "%RTM_JAVA_ROOT%\jar\rtcprof.jar"
java -cp "%CLASSPATH%;" rtcprof.rtcprof %1

