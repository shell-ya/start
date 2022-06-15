package com.starnft.star.application.process.limit;

public interface ICurrentLimiter {

    boolean tryAcquire();
}
