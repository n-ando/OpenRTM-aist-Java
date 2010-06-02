#!/bin/sh
#
#

export LC_ALL=C
cd jp.go.aist.rtm.RTC
ant idlCompile 
if [ $? -ne 0 ];
then 
 exit 1
fi
ant javaDoc 
if [ $? -ne 0 ];
then 
 exit 1
fi
cd ..


