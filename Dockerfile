FROM openjdk:17-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build/libs/uwr-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar","-Djava.security.egd=file:/dev/./urandom", "app.jar"]