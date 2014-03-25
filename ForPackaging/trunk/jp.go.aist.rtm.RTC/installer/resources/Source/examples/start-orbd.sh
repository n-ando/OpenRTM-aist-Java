#!/bin/sh

orbdpath=`which orbd`

echo "Starting Java CORBA naming service (orbd)."

if test "x$orbdpath" = "x" ; then
	echo "orbd not found."
	echo "The orbd is included in Sun JDK."
	echo "Please install Sun JDK."
	exit 1
fi

if test "x$1" = "x" ; then
	port=2809
else
	port=$1
fi
$orbdpath -ORBInitialPort $port -ORBInitialHost localhost
