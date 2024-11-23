# Railway Management API

Generated
by [Spring Initializr](https://start.spring.io/#!type=gradle-project-kotlin&language=java&platformVersion=3.3.5&packaging=jar&jvmVersion=23&groupId=dev.fresult&artifactId=railwayManagement&name=Railway%20Management%20API&description=To%20manage%20railway%20and%20rail%20ticket.&packageName=dev.fresult.railwayManagement&dependencies=devtools,web,data-jdbc,security,postgresql,flyway,validation)

## Prerequisite
1. Java Development Kit (JDK) version 23
2. IntelliJ IDEA (or any preferred IDE)
3. Docker and Docker Compose

## In development mode

### How to start the project

1. Copy the `.env.example` file to `.env` and fill in the necessary values.

    ```bash
    cp .env.example .env
    ```

   > **Note:**
   >
   > For the learning purpose, the environment variables are not confidential, so let's fill these
   >
   > ```text
    > DATABASE_URL=jdbc:postgresql://localhost:5433/railway_management
    > 
    > DATABASE_NAME=railway_management
    > DATABASE_USER=railmaster
    > DATABASE_PASSWORD=secret
    > ```

2. Run the following command to start the project

    ```bash
    docker-compose -f docker/compose.yml up -d
    ```

3. Build the project
    ```bash
    ./gradlew clean build
    ```

### How to start application in development

```bash
./gradlew bootRun
```

### How to see the API documentation (OpenAPI)
- Open the browser and go to <http://localhost:8080/swagger-ui/index.html>
