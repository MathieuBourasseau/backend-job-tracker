# Stage 1: Build the application using Maven and Java 25
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Set memory limits for Maven to prevent "Out of Memory" crashes on free-tier servers
ENV MAVEN_OPTS="-Xmx256m"

# Build the application, skipping tests to speed up the process
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime environment
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the generated jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
