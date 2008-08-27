set DUMMY=%ANT_HOME%
set ANT_HOME=%ECLIPSE_HOME%\plugins\org.apache.ant_1.6.5\

cd jp.go.aist.rtm.rtctemplate
call ant buildAll -lib %ECLIPSE33_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\ -lib %ECLIPSE33_HOME%\plugins\ -lib %ECLIPSE33_HOME%\plugins\org.junit.source_3.8.2.v200706111738\
cd ..

set ANT_HOME=%DUMMY%

