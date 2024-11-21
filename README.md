# Railway Management API

## How to start the project

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

## How to start application in development

```bash
./gradlew bootRun
```
