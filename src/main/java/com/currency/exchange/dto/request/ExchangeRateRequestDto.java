package com.currency.exchange.dto.request;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.ValidationException;

public record ExchangeRateRequestDto(String requestedCurrencies) implements BaseDto {
    public ExchangeRateRequestDto {

        if (requestedCurrencies == null || requestedCurrencies.equals("/")) {
            throw new ValidationException("Currency pair is required");
        }
        requestedCurrencies = requestedCurrencies.replace("/", "");
    }

    @Override
    public void validate() {
        if (requestedCurrencies.length() != 6) {
            throw new ValidationException("Currency pair must contain exactly 6 characters");
        }

        for (Character c : requestedCurrencies.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Currency codes must contain letters only");
            }
        }
    }
}
