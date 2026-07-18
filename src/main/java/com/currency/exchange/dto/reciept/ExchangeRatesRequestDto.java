package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record ExchangeRatesRequestDto(String baseCurrencyCode, String targetCurrencyCode,
                                      Double rate) implements BaseDto {

    public ExchangeRatesRequestDto {
        if (baseCurrencyCode == null) {
            throw new InvalidFormatException("Base currency code is required");
        }

        if (targetCurrencyCode == null) {
            throw new InvalidFormatException("Target currency code is required");
        }

        if (rate == null) {
            throw new InvalidFormatException("Exchange rate is required");
        }
    }


    @Override
    public void validate() {
        if (baseCurrencyCode.length() != 3) {
            throw new InvalidFormatException("Base currency code must be exactly 3 characters long");
        }

        for (Character c : baseCurrencyCode.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new InvalidFormatException("Base currency code must contain letters only");
            }
        }

        if (targetCurrencyCode.length() != 3) {
            throw new InvalidFormatException("Target currency code must be exactly 3 characters long");
        }

        for (Character c : targetCurrencyCode.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new InvalidFormatException("Target currency code must contain letters only");
            }
        }

        if (rate <= 0) {
            throw new InvalidFormatException("Exchange rate must be greater than zero");
        }
    }
}
