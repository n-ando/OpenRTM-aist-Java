#!/bin/sh

if test "x$RTM_JAVA_ROOT" = "x" ; then
    echo "Environment variable RTM_JAVA_ROOT is not set."
    echo "Please specify the OpenRTM-aist installation directory."
    echo "Abort."
    exit 1
fi

class_path=$1 
case $1 in 
	*\.jar )
		class_path=$1 
		;;
	* )
		class_path=$(dirname $1)
		;;
esac

. ./search_classpath.func
export CLASSPATH=`get_classpath`:$class_path
java -cp $CLASSPATH:$RTM_JAVA_ROOT/jar/rtcprof.jar rtcprof.rtcprof ${1+"$@"}

