package com.currency.exchange.dto.request;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.ValidationException;

public record ExchangeRatesRequestDto(String baseCurrencyCode, String targetCurrencyCode,
                                      Double rate) implements BaseDto {

    public ExchangeRatesRequestDto {
        if (baseCurrencyCode == null) {
            throw new ValidationException("Base currency code is required");
        }

        if (targetCurrencyCode == null) {
            throw new ValidationException("Target currency code is required");
        }

        if (rate == null) {
            throw new ValidationException("Exchange rate is required");
        }
    }


    @Override
    public void validate() {
        if (baseCurrencyCode.length() != 3) {
            throw new ValidationException("Base currency code must be exactly 3 characters long");
        }

        for (Character c : baseCurrencyCode.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Base currency code must contain letters only");
            }
        }

        if (targetCurrencyCode.length() != 3) {
            throw new ValidationException("Target currency code must be exactly 3 characters long");
        }

        for (Character c : targetCurrencyCode.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Target currency code must contain letters only");
            }
        }

        if (rate <= 0) {
            throw new ValidationException("Exchange rate must be greater than zero");
        }
    }
}
