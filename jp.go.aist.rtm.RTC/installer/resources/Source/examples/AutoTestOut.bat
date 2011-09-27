set CLASSPATH=.;%RTM_JAVA_ROOT%\jar\OpenRTM-aist-1.1.0.jar;%RTM_JAVA_ROOT%\jar\commons-cli-1.1.jar
java RTMExamples.AutoTest.AutoTestOutComp -f RTMExamples\AutoTest\rtc.conf %*
pause;
