# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
COPY . .
RUN mvn clean package -Pprod -DskipTests

# Copy the pom.xml and download dependencies
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN ./mvnw clean package -Pprod -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /target/identity-0.0.1-SNAPSHOT.jar app.jar

# Expose port 80
EXPOSE 80

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
