# Use Gradle image based on JDK 21 (last version that I found on Docker Hub)
FROM gradle:8.13.0-jdk21 AS build
LABEL author="lillobarilotto"
# Imposta la directory di lavoro
WORKDIR /app

# Copy configuration files and dependencies of Gradle
COPY build.gradle.kts settings.gradle.kts /app/

# Copy the source code
COPY src /app/src

# Copy the waypoints.csv file -> not so good, but it's the only way (NOW) to pass the file to the container, next time I'll use a volume and share the file from other if needed
COPY waypoints.csv /app/waypoints.csv

# Compile the application
RUN gradle build -x test

# Usa a base image with Amazon Corretto 21 and Alpine Linux (not openjdk because seems to be deprecated)
FROM amazoncorretto:21-alpine-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/build/libs/RouteAnalyzer-1.0-SNAPSHOT-all.jar /app/RouteAnalyzer-1.0-SNAPSHOT-all.jar

# Copy the waypoints.csv file from the build stage
COPY --from=build /app/waypoints.csv /app/waypoints.csv

# Execute the application with the waypoints.csv file as argument
ENTRYPOINT ["java", "-jar", "/app/RouteAnalyzer-1.0-SNAPSHOT-all.jar", "/app/waypoints.csv"]