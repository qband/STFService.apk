#!/usr/bin/env bash
set -ex

#cat proto/src/main/proto/wire.proto | grep -oz "MessageType {[^}]*}" | grep -v "{\|}" | sed 's~ \|;~~g' | awk -F= '{print $1" "$2}' | sort -k2 -n
#cat /home/l/Documents/Workspace/reference/stf/lib/wire/wire.proto | grep -oz "MessageType {[^}]*}" | grep -v "{\|}" | sed 's~ \|;~~g' | awk -F= '{print $1" "$2}' | sort -k2 -n

adb shell am stopservice --user 0 -a jp.co.cyberagent.stf.ACTION_START -n jp.co.cyberagent.stf/.Service 2>/dev/null
adb shell am startservice --user 0 -a jp.co.cyberagent.stf.ACTION_START -n jp.co.cyberagent.stf/.Service

adb forward --remove tcp:1100 2>/dev/null && ture
adb forward tcp:1100 tcp:1100

adb shell netstat -nlt | grep 1100
adb forward --list
