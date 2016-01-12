@echo off
set JAR_BASE=%RTM_JAVA_ROOT%\jar\
for /F %%A in ('dir "%JAR_BASE%OpenRTM*" /B') do (set FILE1=%%A)
for /F %%A in ('dir "%JAR_BASE%commons-cli*" /B') do (set FILE2=%%A)
set CLASSPATH=.;%JAR_BASE%%FILE1%;%JAR_BASE%%FILE2%