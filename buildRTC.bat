cd jp.go.aist.rtm.RTC
call ant buildAll -lib %ANT_HOME%\lib\
if ERRORLEVEL 1 goto error_end 
cd ..
exit /b 0

:error_end
cd ..
exit /b 1

