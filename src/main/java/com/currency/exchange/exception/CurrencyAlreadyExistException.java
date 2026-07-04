package com.currency.exchange.exception;

public class CurrencyAlreadyExistException extends RuntimeException {
    public CurrencyAlreadyExistException(String message) {
        super(message);
    }
}
