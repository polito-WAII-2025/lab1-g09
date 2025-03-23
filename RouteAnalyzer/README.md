# Route Analyzer and how to use it

This template provides a minimal setup to understand how to use the Route Analyzer.

## Simple usage with Gradlew
You can start with the build of the application and then run the Route Analyzer, using gradlew:
```bash
./gradlew build
./gradlew run --args="waypoints.csv"
```
Remember that our app expects a CSV file with the waypoints, so you need to provide it as an argument.

## Alternative usage with Docker
You can also use Docker to run the Route Analyzer. First, you need to build the Docker image:
```bash
docker build -t route-analyzer .
```
Then, you can run the Route Analyzer using the following command:
```bash
docker run route-analyzer
```
The docker image already include the waypoints.csv file, so you don't need to provide it as an argument.