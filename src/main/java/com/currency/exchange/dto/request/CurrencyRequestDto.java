package com.currency.exchange.dto.request;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.ValidationException;


public record CurrencyRequestDto(String code) implements BaseDto {

    public CurrencyRequestDto {
        if (code == null || code.equals("/")) {
            throw new ValidationException("Currency code is required");
        }
        code = code.replace("/", "").toUpperCase();
    }

    @Override
    public void validate() {
        if (code.length() != 3) {
            throw new ValidationException("Currency code must be exactly 3 characters long");
        }
        if (!code.matches("[A-Za-z]{3}")) {
            throw new ValidationException("Currency code must contain letters only");
        }
    }
}
