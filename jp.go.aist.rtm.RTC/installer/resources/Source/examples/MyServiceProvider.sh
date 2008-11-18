#!/bin/sh

#if test "x$RTM_JAVA_ROOT" = "x" ; then
#    echo "Environment variable RTM_JAVA_ROOT is not set."
#    echo "Please specify the OpenRTM-aist installation directory."
#    echo "Abort."
#    exit 1
#fi

export CLASSPATH=.:../jar/OpenRTM-aist-0.4.0.jar:../jar/commons-cli-1.1.jar
#export CLASSPATH=.:$RTM_JAVA_ROOT/jar/OpenRTM-aist-0.4.1.jar:$RTM_JAVA_ROOT/jar/commons-cli-1.1.jar
java RTMExamples.SimpleService.MyServiceProviderComp -f RTMExamples/SimpleService/rtc.conf
#../../../jdk1.5.0_15/bin/java RTMExamples.SimpleService.MyServiceProviderComp -f RTMExamples/SimpleService/rtc.conf
