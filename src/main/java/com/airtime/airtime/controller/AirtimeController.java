package com.airtime.airtime.controller;

import com.airtime.airtime.service.AirtimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airtime")
public class AirtimeController {

    private final AirtimeService airtimeService;

    public AirtimeController(AirtimeService airtimeService) {
        this.airtimeService = airtimeService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseAirtime(@RequestParam String phoneNumber, @RequestParam double amount) {
        ResponseEntity<String> response = airtimeService.purchaseAirtime(phoneNumber, amount);
        return ResponseEntity.ok(response.getBody());
    }
}
