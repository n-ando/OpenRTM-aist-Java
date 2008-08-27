#!/bin/sh
#
#

set DUMMY=$ANT_HOME
export ANT_HOME=$ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/

#
#
#
cd jp.go.aist.rtm.rtclink
ant buildAll -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib -lib $ECLIPSE_HOME/plugins -lib $ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/lib -lib $ECLIPSE_HOME/plugins/org.junit_3.8.1/
if [ $? -ne 0 ];
 exit 1
fi
echo "--"
cd ..

set ANT_HOME=$DUMMY


