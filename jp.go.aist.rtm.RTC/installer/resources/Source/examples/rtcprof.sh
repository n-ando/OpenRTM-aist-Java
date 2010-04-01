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

#export CLASSPATH=.:$RTM_JAVA_ROOT/jar/OpenRTM-aist-1.0.0.jar:$RTM_JAVA_ROOT/jar/commons-cli-1.1.jar:$(dirname $1)
export CLASSPATH=.:$RTM_JAVA_ROOT/jar/OpenRTM-aist-1.0.0.jar:$RTM_JAVA_ROOT/jar/commons-cli-1.1.jar:$class_path
#java -cp $CLASSPATH -jar $RTM_JAVA_ROOT/jar/rtcprof.jar ${1+"$@"}
java -cp $CLASSPATH:$RTM_JAVA_ROOT/jar/rtcprof.jar rtcprof.rtcprof ${1+"$@"}

