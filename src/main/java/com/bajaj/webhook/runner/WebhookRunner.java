package com.bajaj.webhook.runner;

import com.bajaj.webhook.dto.WebhookResponse;
import com.bajaj.webhook.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebhookRunner implements CommandLineRunner {

    @Autowired
    private WebhookService webhookService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting Bajaj Finserv Health Webhook Project");
        
        try {
            System.out.println("Generating webhook...");
            WebhookResponse webhookResponse = webhookService.generateWebhook();
            
            if (webhookResponse != null && webhookResponse.getWebhook() != null && webhookResponse.getAccessToken() != null) {
                System.out.println("Submitting SQL solution...");
                webhookService.submitSolution(webhookResponse.getWebhook(), webhookResponse.getAccessToken());
                
                System.out.println("Process completed successfully");
            } else {
                System.err.println("Failed to get webhook response");
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
