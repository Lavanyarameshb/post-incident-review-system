package com.internship.tool.config;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import java.time.Duration;
import java.util.Map;

@Component
public class AiServiceClient {

    private final RestTemplate restTemplate;
    
    // ai service base url
    private final String aiServiceUrl = "http://localhost:5000";

    public AiServiceClient(RestTemplateBuilder builder) {
        // setting 10 second timeout
        this.restTemplate = builder
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .build();
    }

    // calling /describe endpoint
    public Map<String, Object> describe(String text) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Map.of("text", text);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    aiServiceUrl + "/describe",
                    request,
                    Map.class
            );
            return response.getBody();
        } catch (Exception e) {
            // returning null if something goes wrong
            System.err.println("Error calling /describe: " + e.getMessage());
            return null;
        }
    }

    // calling /recommend endpoint
    public Map<String, Object> recommend(String text) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Map.of("text", text);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    aiServiceUrl + "/recommend",
                    request,
                    Map.class
            );
            return response.getBody();
        } catch (Exception e) {
            // returning null if something goes wrong
            System.err.println("Error calling /recommend: " + e.getMessage());
            return null;
        }
    }

    // calling /generate-report endpoint
    public Map<String, Object> generateReport(String text) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Map.of("text", text);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    aiServiceUrl + "/generate-report",
                    request,
                    Map.class
            );
            return response.getBody();
        } catch (Exception e) {
            // returning null if something goes wrong
            System.err.println("Error calling /generate-report: " + e.getMessage());
            return null;
        }
    }
}