#!/bin/bash
HOME_DIR=$(pushd $(dirname $0)/.. >/dev/null && pwd -P && popd >/dev/null)
java -Dfile.encoding=UTF-8 -Djava.ext.dirs=${HOME_DIR}/lib -Dconfig.file=${HOME_DIR}/conf/application.conf org.culiu.data.framework.Client