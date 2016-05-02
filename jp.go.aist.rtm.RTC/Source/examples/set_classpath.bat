@call set rtm_java_root=%RTM_JAVA_ROOT%
@set JAR_BASE=%rtm_java_root%\jar\
@for /F %%A in ('dir "%JAR_BASE%OpenRTM*" /B') do (set FILE1=%%A)
@for /F %%A in ('dir "%JAR_BASE%commons-cli*" /B') do (set FILE2=%%A)
@set CLASSPATH=.;%JAR_BASE%%FILE1%;%JAR_BASE%%FILE2%
