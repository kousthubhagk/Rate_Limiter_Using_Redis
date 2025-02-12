package com.example.demo.controller;

import com.example.demo.service.RateLimiterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/data")
    public String getData(@RequestParam String userId) {
        if (rateLimiterService.isAllowed(userId)) {
            return "Request successful for user: " + userId;
        } else {
            return "Too many requests. Please try again later.";
        }
    }
}
