# Bajaj Finserv Health - Spring Boot Webhook Project

Spring Boot application for the Bajaj Finserv Health JAVA qualification challenge.

## Overview

This application:
1. Sends a POST request to generate a webhook on startup
2. Solves an SQL problem based on registration number (REG12347)
3. Submits the solution using access token

## Registration Logic

- **REG12347** ends in **47** (odd number) → Question 1
- Question 1: Find highest salary not paid on 1st day of month
- Question 2: Count younger employees by department (for even numbers)

## API Endpoints

1. **Generate Webhook**: `POST https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
2. **Submit Solution**: `POST https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA`

## Project Structure

```
src/main/java/com/bajaj/webhook/
├── WebhookApplication.java       # Main class
├── dto/                          # Request/Response objects
├── service/WebhookService.java   # Business logic
└── runner/WebhookRunner.java     # Startup runner
```

## Build & Run

```bash
# Run application
mvn spring-boot:run

# Build JAR
mvn clean package

# Execute JAR
java -jar target/webhook-project-0.0.1-SNAPSHOT.jar
```

## SQL Query Implementation

For REG12347 (Question 1):
```sql
SELECT p.AMOUNT as SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME,
       TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) as AGE, d.DEPARTMENT_NAME
FROM PAYMENTS p
JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE DAY(p.PAYMENT_TIME) != 1
ORDER BY p.AMOUNT DESC
LIMIT 1;
```

## Technologies

- Spring Boot 2.7.14
- Maven
- RestTemplate for HTTP calls
- Jackson for JSON processing
