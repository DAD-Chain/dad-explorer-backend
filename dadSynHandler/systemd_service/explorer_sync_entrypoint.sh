#!/bin/sh
install_path=$(cd `dirname $0`;pwd)
jar_package=dadsynhandler-1.1.1-RELEASE.jar
cd ${install_path} && java -jar ${jar_package}