set DUMMY=%ANT_HOME%
set ANT_HOME=%ECLIPSE_HOME%\plugins\org.apache.ant_1.6.5\

cd jp.go.aist.rtm.RTC
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.rtclink
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.rtctemplate
call ant buildAll -lib %ECLIPSE33_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\ -lib %ECLIPSE33_HOME%\plugins\ -lib %ECLIPSE33_HOME%\plugins\org.junit.source_3.8.2.v200706111738\
if ERRORLEVEL 1 goto FAIL
cd ..


cd jp.go.aist.rtm.fsmcbuilder 
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.toolscommon.profiles
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.toolscommon
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
IF ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.nameserviceview 
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.repositoryView
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\ 
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.systemeditor
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.logview
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
if ERRORLEVEL 1 goto FAIL
cd ..

cd jp.go.aist.rtm.rtcbuilder
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
echo --
:FAIL
cd ..

set ANT_HOME=%DUMMY%


