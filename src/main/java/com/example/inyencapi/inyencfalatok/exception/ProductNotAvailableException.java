package com.example.inyencapi.inyencfalatok.exception;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
