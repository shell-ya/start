package com.starnft.star.domain.exercise.core;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

public class ExerciseAccountState extends  AbstractExerciseState {
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    protected void lock() {
//        redisTemplate.opsForHash().put(RedisKey.);
    }

    @Override
    protected void mark() {

    }
}
