#!/bin/sh
#
#

set DUMMY=$ANT_HOME
export ANT_HOME=$ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/

#
#
#

cd jp.go.aist.rtm.RTC
ant buildAllLinux -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib/
if [ $? -ne 0 ];
then 
 exit 1
fi
cd ..


#
#
#
cd jp.go.aist.rtm.fsmcbuilder 
ant buildAll -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib/ -lib $ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/lib/ -lib $ECLIPSE_HOME/plugins/org.junit_3.8.1/ -lib $ECLIPSE_HOME/plugins
if [ $? -ne 0 ];
then 
 exit 1
fi
cd ..



#
#
#
cd jp.go.aist.rtm.toolscommon.profiles
ant buildAll -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib/ -lib $ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/lib/ -lib $ECLIPSE_HOME/plugins/org.junit_3.8.1/ -lib $ECLIPSE_HOME/plugins
if [ $? -ne 0 ];
then 
 exit 1
fi
cd ..




#
#
#
cd jp.go.aist.rtm.logview
ant buildAll -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib/ -lib $ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/lib/ -lib $ECLIPSE_HOME/plugins/org.junit_3.8.1/ -lib $ECLIPSE_HOME/plugins
if [ $? -ne 0 ];
then 
 exit 1
fi
cd ..

#
#
#
cd jp.go.aist.rtm.rtcbuilder
ant buildAll -lib $ECLIPSE_HOME/plugins/net.sf.ant4eclipse.plugin_0.5.0.rc1/lib/ -lib $ECLIPSE_HOME/plugins/org.apache.ant_1.6.5/lib/ -lib $ECLIPSE_HOME/plugins/org.junit_3.8.1/ -lib $ECLIPSE_HOME/plugins
if [ $? -ne 0 ];
then 
 exit 1
fi
echo "--"
cd ..

set ANT_HOME=$DUMMY


