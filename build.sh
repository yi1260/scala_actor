#!/bin/bash
MODULE=$1
ARTIFACT_ID=${MODULE}

HOME_DIR=$(pushd $(dirname $0)/ >/dev/null && pwd -P && popd >/dev/null)

cd ${HOME_DIR}/${MODULE}

mvn assembly:assembly && {
  cd ..
  mkdir lib
  cp ${HOME_DIR}/${MODULE}/target/${ARTIFACT_ID}-1.0-SNAPSHOT-allinone.jar ${HOME_DIR}/lib/${ARTIFACT_ID}-1-0-SNAPSHOT-allinone.jar
} || {
  echo "Build failed"
  exit 255
}
exit 0