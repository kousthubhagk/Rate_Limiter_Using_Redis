package com.example.demo.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final int MAX_REQUESTS = 5;  // Limit: 5 requests per minute

    public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String userId) {
        String key = "rate_limit:" + userId;
        Long requestCount = redisTemplate.opsForValue().increment(key);

        if (requestCount == 1) {
            // Set expiration for the key to 1 minute
            redisTemplate.expire(key, Duration.ofMinutes(1));
        }

        return requestCount <= MAX_REQUESTS;
    }
}
