FROM openjdk:11-jre
LABEL maintainer="li.zeqing@outlook.com"

VOLUME /tmp
ADD target/*.jar /app.jar

ENV JAVA_OPTS="-Duser.timezone=Asia/Shanghai"
ENV APP_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $APP_OPTS" ]

