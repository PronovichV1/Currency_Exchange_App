package com.currency.exchange.dto.request;

import com.currency.exchange.exception.ValidationException;

public record ExchangeRateRequestDto(String requestedCurrencies) {
    public ExchangeRateRequestDto {

        if (requestedCurrencies == null || requestedCurrencies.equals("/")) {
            throw new ValidationException("Currency pair is required");
        }
        requestedCurrencies = requestedCurrencies.replace("/", "");
    }

}
