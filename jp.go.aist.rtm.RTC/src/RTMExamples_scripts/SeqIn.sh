#!/bin/sh

if test "x$RTM_JAVA_ROOT" = "x" ; then
    echo "Environment variable RTM_JAVA_ROOT is not set."
    echo "Please specify the OpenRTM-aist installation directory."
    echo "Abort."
    exit 1
fi
cd `dirname $0`
. ./search_classpath.func
export CLASSPATH=`get_classpath`
java RTMExamples.SeqIO.SeqInComp -f RTMExamples/SeqIO/rtc.conf ${1+"$@"}
