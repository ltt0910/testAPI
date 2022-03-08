package com.example.test3.exception;

public class OptimisticLockingExeption extends Exception {
    public OptimisticLockingExeption(String message) {
        super(message);
    }
}
