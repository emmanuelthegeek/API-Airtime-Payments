package com.airtime.airtime.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AirtimeService {

    private final RestTemplate restTemplate;

    // Airtime VTU API base URL
    @Value("${vtu.api.url}")
    private String vtuApiUrl;

    // Authorization token for the VTU API
    @Value("${vtu.api.token}")
    private String vtuApiToken;

    public AirtimeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // This is the method to purchase airtime
    public ResponseEntity<String> purchaseAirtime(String phoneNumber, double amount) {
        String endpointUrl = vtuApiUrl + "/purchase";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + vtuApiToken);
        headers.set("Content-Type", "application/json");

        // Request body created
        String requestBody = String.format("{\"phoneNumber\": \"%s\", \"amount\": %.2f}", phoneNumber, amount);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(endpointUrl, HttpMethod.POST, requestEntity, String.class);
            return response;
        } catch (Exception e) {

            throw new RuntimeException("Failed to purchase airtime: " + e.getMessage(), e);
        }
    }
}
