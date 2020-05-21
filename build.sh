#!/bin/bash
cd `dirname $0`

img_mvn="maven:3.3.3-jdk-8"                 # docker image of maven
m2_cache=~/.m2                              # the local maven cache dir
proj_home=$PWD                              # the project root dir

git pull  # should use git clone https://name:pwd@xxx.git

echo "use docker maven"
docker run --rm \
   -v $m2_cache:/root/.m2 \
   -v $proj_home:/usr/src/mymaven \
   -w /usr/src/mymaven $img_mvn mvn clean package -U -Dmaven.test.skip=true

mv $proj_home/tt-user-center-provider/target/tt-user-center-provider-*.jar $proj_home/tt-user-center-provider/target/demo.jar

echo "构建镜像"
docker build -t $APP_NAME:v$VERSION .