FROM maven:3.9.6-eclipse-temurin-8 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY WebContent ./WebContent

RUN mvn -e -X clean package

FROM tomcat:8-jdk8

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/_00_ASBank2023-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ASBank2023.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
