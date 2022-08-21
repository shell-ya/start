package com.starnft.star.application.process.limit.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.starnft.star.application.process.limit.ICurrentLimiter;
import org.springframework.stereotype.Service;

@Service
public class CurrentLimiter implements ICurrentLimiter {

    private final RateLimiter rateLimiter = RateLimiter.create(1000);

    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
}
