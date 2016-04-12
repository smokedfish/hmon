#!/bin/bash

SCRIPT_HOME="$(cd -P "$(dirname "$0")" && pwd)"
HMON_HOME="$(dirname "${SCRIPT_HOME}")"

java -Dhmon.home=${HOMN_HOME} -cp "${HMON_HOME}/*:${HMON_HOME}/lib/*" org.hmon.server.ServerMain

