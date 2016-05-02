#!/bin/bash

SCRIPT_HOME="$(cd -P "$(dirname "$0")" && pwd)"
HMON_HOME="$(dirname "${SCRIPT_HOME}")"

java -Dhmon.home=${HMON_HOME} -Dlog4j.configuration=file:${HMON_HOME}/etc/log4j.properties -cp "${HMON_HOME}/*:${HMON_HOME}/lib/*" org.hmon.server.ServerMain

