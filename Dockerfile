# Build stage
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml mvnw mvnw.cmd .
COPY .mvn .mvn
RUN chmod +x mvnw
COPY src src
RUN ./mvnw -DskipTests package

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
ENV PORT=8080
COPY --from=build /app/target/quiz-seguranca-digital-1.0.0.jar app.jar
EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
