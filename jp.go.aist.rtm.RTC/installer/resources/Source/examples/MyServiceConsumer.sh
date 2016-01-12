#!/bin/sh

if test "x$RTM_JAVA_ROOT" = "x" ; then
    echo "Environment variable RTM_JAVA_ROOT is not set."
    echo "Please specify the OpenRTM-aist installation directory."
    echo "Abort."
    exit 1
fi

. ./search_classpath.func
export CLASSPATH=`get_classpath`
java RTMExamples.SimpleService.MyServiceConsumerComp -f RTMExamples/SimpleService/rtc.conf ${1+"$@"}
