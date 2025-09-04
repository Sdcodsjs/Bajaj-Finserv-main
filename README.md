# Bajaj Finserv Health - Spring Boot Webhook Project

Spring Boot app for the Bajaj Finserv Health JAVA challenge.

## Overview
- Generates a webhook on startup  
- Solves SQL problem (based on reg no.)  
- Submits solution using JWT access token  

## Registration Logic
- REG123 → odd → Question 1  
- Q1: Highest salary not paid on 1st day of month
## SQL Query
 (Question 1):
```sql
  SELECT p.AMOUNT AS SALARY, 
       CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME,
       TIMESTAMPDIFF(YEAR, e.DOB, DATE(p.PAYMENT_TIME)) AS AGE,
       d.DEPARTMENT_NAME
FROM PAYMENTS p
JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE DAY(p.PAYMENT_TIME) <> 1
ORDER BY p.AMOUNT DESC
LIMIT 1;


## Build & Run
```bash
mvn clean package
java -jar target/webhook-project-0.0.1-SNAPSHOT.jar
