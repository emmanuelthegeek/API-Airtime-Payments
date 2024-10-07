package com.airtime.airtime.controller;

import com.airtime.airtime.service.AirtimeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AirtimeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AirtimeService airtimeService;

    @Value("${vtu.api.token}")
    private String vtuApiToken = "your_vtu_api_token"; // Mock value for testing

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchaseAirtime_Success() {
        String phoneNumber = "08012345678";
        double amount = 100.0;

        // Mocking the response from the RestTemplate
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Success");
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        ResponseEntity<String> response = airtimeService.purchaseAirtime(phoneNumber, amount);

        assertEquals("Success", response.getBody());
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testPurchaseAirtime_Failure() {
        String phoneNumber = "08012345678";
        double amount = 100.0;

        // Mocking an exception
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("API error"));

        RuntimeException exception = null;
        try {
            airtimeService.purchaseAirtime(phoneNumber, amount);
        } catch (RuntimeException e) {
            exception = e;
        }

        assertEquals("Failed to purchase airtime: API error", exception.getMessage());
    }
}

