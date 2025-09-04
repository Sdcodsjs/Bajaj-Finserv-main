# Bajaj Finserv Health - Spring Boot Webhook Project

Spring Boot app for the Bajaj Finserv Health JAVA challenge.

## Overview
- Generates a webhook on startup  
- Solves SQL problem (based on reg no.)  
- Submits solution using JWT access token  

## Registration Logic
- REG12333 → odd → Question 1  
- Q1: Highest salary not paid on 1st day of month  

## Build & Run
```bash
mvn clean package
java -jar target/webhook-project-0.0.1-SNAPSHOT.jar
