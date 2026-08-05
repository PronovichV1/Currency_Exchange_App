package com.currency.exchange.validator;

public interface Validator<T> {
    void validate(T target);
}
