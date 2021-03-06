#!/usr/bin/env bash

_PROJECT_PATH='/workon/m2/mvn-repo'

git status
git add ./
git commit -m "ZTONE_LANG"
git push origin master

if (( $# == 0 )) || [[ -z $1 ]]; then
    ./gradlew -q -p ztone-lang clean build install bintrayUpload

else
    _MODULE_NAME=$1

    ./gradlew -q -p ${_MODULE_NAME} clean build install bintrayUpload
fi

#git -C ${_PROJECT_PATH} status
#git -C ${_PROJECT_PATH} add ./
#git -C ${_PROJECT_PATH} commit -m "ZTONE_LANG"
#git -C ${_PROJECT_PATH} push github master
