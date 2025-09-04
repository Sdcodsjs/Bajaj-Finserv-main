package com.bajaj.webhook.service;

import com.bajaj.webhook.dto.WebhookRequest;
import com.bajaj.webhook.dto.WebhookResponse;
import com.bajaj.webhook.dto.SubmissionRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private static final String GENERATE_WEBHOOK_ENDPOINT = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SUBMIT_ENDPOINT = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    private static final String NAME = "John Doe";
    private static final String REG_NO = "REG12347";
    private static final String EMAIL = "john@example.com";
    
    private final RestTemplate restTemplate;

    public WebhookService() {
        this.restTemplate = new RestTemplate();
    }

    public WebhookResponse generateWebhook() {
        try {
            System.out.println("Generating webhook for regNo: " + REG_NO);
            
            WebhookRequest request = new WebhookRequest(NAME, REG_NO, EMAIL);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                GENERATE_WEBHOOK_ENDPOINT,
                HttpMethod.POST,
                entity,
                WebhookResponse.class
            );
            
            WebhookResponse webhookResponse = response.getBody();
            System.out.println("Webhook generated successfully: " + webhookResponse);
            
            return webhookResponse;
            
        } catch (Exception e) {
            System.err.println("Error generating webhook: " + e.getMessage());
            throw new RuntimeException("Failed to generate webhook", e);
        }
    }

    public String getSqlQuery() {
        int lastTwoDigits = Integer.parseInt(REG_NO.substring(REG_NO.length() - 2));
        boolean isOdd = lastTwoDigits % 2 != 0;
        
        if (isOdd) {
            return "SELECT p.AMOUNT as SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME, " +
                   "TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) as AGE, d.DEPARTMENT_NAME " +
                   "FROM PAYMENTS p " +
                   "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                   "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                   "WHERE DAY(p.PAYMENT_TIME) != 1 " +
                   "ORDER BY p.AMOUNT DESC " +
                   "LIMIT 1;";
        } else {
            return "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, " +
                   "COUNT(e2.EMP_ID) as YOUNGER_EMPLOYEES_COUNT " +
                   "FROM EMPLOYEE e1 " +
                   "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                   "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e1.DOB " +
                   "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                   "ORDER BY e1.EMP_ID DESC;";
        }
    }

    public void submitSolution(String webhookUrl, String accessToken) {
        try {
            String sqlQuery = getSqlQuery();
            System.out.println("Submitting SQL query: " + sqlQuery);
            
            SubmissionRequest request = new SubmissionRequest(sqlQuery);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);
            
            HttpEntity<SubmissionRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                SUBMIT_ENDPOINT,
                HttpMethod.POST,
                entity,
                String.class
            );
            
            System.out.println("Solution submitted successfully. Response: " + response.getBody());
            
        } catch (Exception e) {
            System.err.println("Error submitting solution: " + e.getMessage());
            throw new RuntimeException("Failed to submit solution", e);
        }
    }
}
