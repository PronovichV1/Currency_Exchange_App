package com.currency.exchange.dto.request;

import com.currency.exchange.exception.ValidationException;

public record ExchangeRatesRequestDto(String baseCurrencyCode, String targetCurrencyCode,
                                      String rate) {

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

        baseCurrencyCode = baseCurrencyCode.trim().toUpperCase();
        targetCurrencyCode = targetCurrencyCode.trim().toUpperCase();
    }

}
