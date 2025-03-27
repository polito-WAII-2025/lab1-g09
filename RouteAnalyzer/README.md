# Route Analyzer and how to use it

This template provides a minimal setup to understand how to use the Route Analyzer.

## Simple usage with Gradlew
You can start with the build of the application and then run the Route Analyzer, using gradlew:
```bash
./gradlew build
 ./gradlew run --args="./src/main/resources/waypoints.csv ./src/main/resources/output.json ./src/main/resources/output-advanced.json ./src/main/resources/custom-parameters.yml" 
```
Remember that our app expects some arguments, these is the order of the arguments for the command above:
```bash
 ./gradlew run --args="<input-file> <output-file> <output-advanced-file> <custom-parameters-file>" 
```

## Alternative usage with Docker
### Build and run the Docker image
You can also use Docker to run the Route Analyzer. First, you need to build the Docker image:
```bash
docker build -t route-analyzer .
```
Then, you can run the Route Analyzer using the following command:
```bash
docker run -v $pwd/src/main/resources:/app/resources route-analyzer /app/resources/waypoints.csv /app/resources/output.json /app/resources/output-advanced.json /app/resources/custom-parameters.yml
```

This command mounts the `src/main/resources` directory from your host (starting from the path where it is launched) into the `/app/resources` directory of the container and passes the necessary files as arguments.
Please note that `$pwd` is used to get the current path. The following command has been tested on a Windows system, under Git Bash environment on IntelliJ IDEA.
To achieve the same result but with greater simplicity, it is recommended to use `docker-compose`.

### Use docker-compose to run the Route Analyzer
You can also use `docker-compose` to run the Route Analyzer. First, you need to build the Docker image (remember to be in the RouteAnalyzer directory when you run this command):
```bash
docker-compose build
```
Then, you can run the Route Analyzer using the following command:
```bash
docker-compose up
```
The docker-compose file is already configured to mount the `src/main/resources` directory from your host into the `/app/resources` directory of the container and pass the necessary files as arguments.
Please note that the `docker-compose up` command is configured to run the Route Analyzer with the default args specified into the docker-compose file.

If you want to run the Route Analyzer with custom parameters, you can run the following command:
```bash
docker-compose run route-analyzer /app/resources/waypoints.csv /app/resources/output.json /app/resources/output-advanced.json /app/resources/custom-parameters.yml
```
