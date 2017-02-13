#!/bin/bash

HOME_DIR=$(pushd $(dirname $0)/ >/dev/null && pwd -P && popd >/dev/null)

mvn assembly:assembly && {
  cd ..
  mkdir lib
  cp ${HOME_DIR}/target/test-scala-akka-1.0-SNAPSHOT-allinone.jar ${HOME_DIR}/lib/test-scala-akka-1-0-SNAPSHOT-allinone.jar
} || {
  echo "Build failed"
  exit 255
}
exit 0