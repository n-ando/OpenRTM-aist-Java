#!/usr/bin/env python
#
# @brief WiX wxs file generator for OpenRTM-aist-Java
# @date $Date$
# @author Norkai Ando <n-ando@aist.go.jp>
#
# Copyright (C) 2010
#     Noriaki Ando
#     Intelligent Systems Research Institute,
#     National Institute of
#         Advanced Industrial Science and Technology (AIST), Japan
#     All rights reserved.
#
# $Id$
#

import os
import shutil
import glob
import makewxs

data = [
    ("Source/jar",                                                      "*.jar"),
    ("Source/examples/bat",                                             "*.bat"),
    ("Source/examples/bin",                                             "*.vbs *.bat *.conf"),
    ("Source/examples/RTMExamples/Composite",                           "*.class *.java *.conf"),
    ("Source/examples/RTMExamples/ConfigSample",                        "*.class *.java *.conf"),
    ("Source/examples/RTMExamples/GUIIn",                               "*.class *.java *.conf"),
    ("Source/examples/RTMExamples/GUIIn/control",                       "*.class *.java"),
    ("Source/examples/RTMExamples/GUIIn/model",                         "*.class *.java"),
    ("Source/examples/RTMExamples/GUIIn/view",                          "*.class *.java"),
    ("Source/examples/RTMExamples/SeqIO",                               "*.class *.java *.conf"),
    ("Source/examples/RTMExamples/SeqIO/view",                          "*.class *.java"),
    ("Source/examples/RTMExamples/SimpleIO",                            "*.class *.java *.conf"),
    ("Source/examples/RTMExamples/SimpleService",                       "*.class *.java *.conf *.idl"),
    ("Source/examples/RTMExamples/SinCosOut",                           "*.class *.java *.conf"),
    ("Source/JavaDoc",                                                  "*.html *.css package-list"),
    ("Source/JavaDoc/_SDOPackage",                                      "*.html"),
    ("Source/JavaDoc/_SDOPackage/class-use",                            "*.html"),
    ("Source/JavaDoc/index-files",                                      "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm",                                   "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/class-use",                         "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC",                               "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/buffer",                        "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/buffer/class-use",              "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/class-use",                     "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/executionContext",              "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/executionContext/class-use",    "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/log",                           "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/log/class-use",                 "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/port",                          "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/port/class-use",                "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/port/publisher",                "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/port/publisher/class-use",      "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/SDOPackage",                    "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/SDOPackage/class-use",          "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/util",                          "*.html"),
    ("Source/JavaDoc/jp/go/aist/rtm/RTC/util/class-use",                "*.html"),
    ("Source/JavaDoc/OpenRTM",                                          "*.html"),
    ("Source/JavaDoc/OpenRTM/class-use",                                "*.html"),
    ("Source/JavaDoc/resources",                                        "*.gif"),
    ("Source/JavaDoc/RTC",                                              "*.html"),
    ("Source/JavaDoc/RTC/class-use",                                    "*.html"),
    ("Source/JavaDoc/RTM",                                              "*.html"),
    ("Source/JavaDoc/RTM/class-use",                                    "*.html"),
    ("Source/JavaDoc/RTMExamples/Composite",                            "*.html"),
    ("Source/JavaDoc/RTMExamples/Composite/class-use",                  "*.html"),
    ("Source/JavaDoc/RTMExamples/ConfigSample",                         "*.html"),
    ("Source/JavaDoc/RTMExamples/ConfigSample/class-use",               "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn",                                "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn/class-use",                      "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn/control",                        "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn/control/class-use",              "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn/model",                          "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn/model/class-use",                "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn/view",                           "*.html"),
    ("Source/JavaDoc/RTMExamples/GUIIn/view/class-use",                 "*.html"),
    ("Source/JavaDoc/RTMExamples/SeqIO",                                "*.html"),
    ("Source/JavaDoc/RTMExamples/SeqIO/class-use",                      "*.html"),
    ("Source/JavaDoc/RTMExamples/SeqIO/view",                           "*.html"),
    ("Source/JavaDoc/RTMExamples/SeqIO/view/class-use",                 "*.html"),
    ("Source/JavaDoc/RTMExamples/SimpleIO",                             "*.html"),
    ("Source/JavaDoc/RTMExamples/SimpleIO/class-use",                   "*.html"),
    ("Source/JavaDoc/RTMExamples/SimpleService",                        "*.html"),
    ("Source/JavaDoc/RTMExamples/SimpleService/class-use",              "*.html"),
    ("Source/JavaDoc/RTMExamples/SinCosOut",                            "*.html"),
    ("Source/JavaDoc/RTMExamples/SinCosOut/class-use",                  "*.html"),
    ("Source/JavaDocEn",                                                "*.html *.css package-list"),
    ("Source/JavaDocEn/_SDOPackage",                                    "*.html"),
    ("Source/JavaDocEn/_SDOPackage/class-use",                          "*.html"),
    ("Source/JavaDocEn/index-files",                                    "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm",                                 "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/class-use",                       "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC",                             "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/buffer",                      "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/buffer/class-use",            "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/class-use",                   "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/executionContext",            "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/executionContext/class-use",  "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/log",                         "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/log/class-use",               "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/port",                        "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/port/class-use",              "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/port/publisher",              "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/port/publisher/class-use",    "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/SDOPackage",                  "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/SDOPackage/class-use",        "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/util",                        "*.html"),
    ("Source/JavaDocEn/jp/go/aist/rtm/RTC/util/class-use",              "*.html"),
    ("Source/JavaDocEn/OpenRTM",                                        "*.html"),
    ("Source/JavaDocEn/OpenRTM/class-use",                              "*.html"),
    ("Source/JavaDocEn/resources",                                      "*.gif"),
    ("Source/JavaDocEn/RTC",                                            "*.html"),
    ("Source/JavaDocEn/RTC/class-use",                                  "*.html"),
    ("Source/JavaDocEn/RTM",                                            "*.html"),
    ("Source/JavaDocEn/RTM/class-use",                                  "*.html"),
    ("Source/JavaDocEn/RTMExamples/Composite",                          "*.html"),
    ("Source/JavaDocEn/RTMExamples/Composite/class-use",                "*.html"),
    ("Source/JavaDocEn/RTMExamples/ConfigSample",                       "*.html"),
    ("Source/JavaDocEn/RTMExamples/ConfigSample/class-use",             "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn",                              "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn/class-use",                    "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn/control",                      "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn/control/class-use",            "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn/model",                        "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn/model/class-use",              "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn/view",                         "*.html"),
    ("Source/JavaDocEn/RTMExamples/GUIIn/view/class-use",               "*.html"),
    ("Source/JavaDocEn/RTMExamples/SeqIO",                              "*.html"),
    ("Source/JavaDocEn/RTMExamples/SeqIO/class-use",                    "*.html"),
    ("Source/JavaDocEn/RTMExamples/SeqIO/view",                         "*.html"),
    ("Source/JavaDocEn/RTMExamples/SeqIO/view/class-use",               "*.html"),
    ("Source/JavaDocEn/RTMExamples/SimpleIO",                           "*.html"),
    ("Source/JavaDocEn/RTMExamples/SimpleIO/class-use",                 "*.html"),
    ("Source/JavaDocEn/RTMExamples/SimpleService",                      "*.html"),
    ("Source/JavaDocEn/RTMExamples/SimpleService/class-use",            "*.html"),
    ("Source/JavaDocEn/RTMExamples/SinCosOut",                          "*.html"),
    ("Source/JavaDocEn/RTMExamples/SinCosOut/class-use",                "*.html")
]

## Resource path
##
base_dir = os.getenv("OPENRTM_JAVA")
if base_dir == None:
    base_dir = "C:\\distribution\\OpenRTM-aist-Java-1.0.0\\"
else:
    base_dir = base_dir.replace("\"", "")
    base_dir += "\\"


## make temporary files
##
temp_dir = base_dir + "Source\\examples\\bin"
bin_list = glob.glob(base_dir + "Source\\examples\\*.vbs")
bin_cnt = len(bin_list)
if os.path.exists(temp_dir) :
    shutil.rmtree(temp_dir)
os.mkdir(temp_dir)
for i in range(bin_cnt):
    shutil.copy2(bin_list[i], temp_dir)

## tools *.bat choice
temp_dir = base_dir + "Source\\examples\\bin"
bat_dir = base_dir + "Source\\examples\\"
bat_list = ["rtcd.bat", "rtcprof.bat", "rtcd_java.conf"]
bin_cnt = len(bat_list)
bin_list = []
for i in range(bin_cnt):
    bin_list.append(bat_dir + bat_list[i])
for i in range(bin_cnt):
    shutil.copy2(bin_list[i], temp_dir)

## Shortcut examples *.bat choice
temp_dir = base_dir + "Source\\examples\\bat"
bat_dir = base_dir + "Source\\examples\\"
bat_list = ["Composite.bat", "ConfigSample.bat", "ConsoleIn.bat", "ConsoleOut.bat", 
            "GUIIn.bat", "MyServiceConsumer.bat", "MyServiceProvider.bat", 
            "SeqIn.bat", "SeqOut.bat", "SinCosOut.bat"]
bin_cnt = len(bat_list)
bin_list = []
for i in range(bin_cnt):
    bin_list.append(bat_dir + bat_list[i])
if os.path.exists(temp_dir) :
    shutil.rmtree(temp_dir)
os.mkdir(temp_dir)
for i in range(bin_cnt):
    shutil.copy2(bin_list[i], temp_dir)


def path_to_dir_id(path, prefix):
    # path = "bin/x86_win32" prefix = "omni"
    # output = "omni_bin_x86_win32"
    # "." -> "_"
    output = prefix + "_" + "_".join(path.split("/"))
    return output.replace(".", "_")

def path_to_comp_id(path, prefix):
    # path = "bin/x86_win32" prefix = "omni"
    # output = "OmniBinX86_win32"
    # "." -> "_"
    output = prefix.capitalize()
    for c in path.split("/"):
        output += c.capitalize()
    return output.replace(".", "_")


## make yaml file
##
for (path, files) in data:
    # wxs component name
    comp_name = path_to_comp_id(path, "rtm")
    comp_name = comp_name.replace("-", "")
    # wxs directory name
    dir_name = path_to_dir_id(path, "rtm")

    path = path.replace("/", "\\")

    # full path to target directory
    full_path = base_dir + "\\" + path

    flist = []
    for f in files.split(" "):
        flist += glob.glob(full_path + "\\" + f)

    curr_path = base_dir + path
    if curr_path.rfind("\\") == (len(curr_path) -1):
        curr_path = curr_path[0:len(curr_path)-1]

    cmd = ["flist",
           "-c", comp_name,
           "-o", dir_name + ".yaml",
           "-p", curr_path]
    cmd += flist
    makewxs.main(cmd)


## make wxs file
##
cmd = ["wxs",
       "-o", "OpenRTM-aist-Java.wxs",
       "-i", "OpenRTM-aist-Java.wxs.in"]
cmd += glob.glob("*.yaml")
makewxs.main(cmd)

