FROM maven:3-openjdk-11-slim as builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . ./
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/film-database-api-0.0.1-SNAPSHOT.jar /app/film-database-api.jar
#RUN chmod +x film-database-api.jar
EXPOSE 8080
CMD ["java", "-jar", "film-database-api.jar"]