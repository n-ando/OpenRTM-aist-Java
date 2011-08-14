set DUMMY=%ANT_HOME%
set ANT_HOME=%ECLIPSE_HOME%\plugins\org.apache.ant_1.6.5\


cd jp.go.aist.rtm.RTC
call ant buildAll -lib %ECLIPSE_HOME%\plugins\net.sf.ant4eclipse.plugin_0.5.0.rc1\lib\
cd ..


set ANT_HOME=%DUMMY%


