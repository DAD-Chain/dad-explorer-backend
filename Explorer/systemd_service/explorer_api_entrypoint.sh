#!/bin/sh
install_path=$(cd `dirname $0`;pwd)
jar_package=explorer-1.2.2.RELEASE.jar
cd ${install_path} && java -jar ${jar_package}