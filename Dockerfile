# 建议生产使用，ref: http://blog.tenxcloud.com/?p=1894
FROM fabric8/java-jboss-openjdk8-jdk

USER root

#中文乱码问题
ENV LC_ALL en_US.UTF-8
ENV LANG en_US.UTF-8

# Prepare by downloading dependencies
COPY tt-user-center-provider/target/demo.jar /home/