#!/bin/sh
#
#

DUMMY=$ANT_HOME
export ANT_HOME=$ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/

 

if test $# -eq 0 ; then
    var_for="buildAllLinux"
else
    var_for=$1
fi

#
#
#
cd jp.go.aist.rtm.RTC
#ant buildAllLinux -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib/
ant $var_for -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib/
if [ $? -ne 0 ];
then 
    exit 1
fi
echo "--"
cd ..

export ANT_HOME=$DUMMY


