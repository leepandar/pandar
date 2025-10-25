FROM registry.cn-hangzhou.aliyuncs.com/dockerhub_mirror/java:21-anolis

WORKDIR /pandar

ARG JAR_FILE=target/pandar.jar

COPY ${JAR_FILE} app.jar

EXPOSE 9527

ENV TZ=Asia/Shanghai DEFAULT_JVM_OPTS="-Xms512m -Xmx8g -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "exec java ${JVM_OPTS:-$DEFAULT_JVM_OPTS} -jar app.jar"]
