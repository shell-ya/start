package com.starnft.star.domain.exercise.core;

public abstract class AbstractExerciseState {
    abstract  protected  void lock();
    abstract  protected  void mark();
}
