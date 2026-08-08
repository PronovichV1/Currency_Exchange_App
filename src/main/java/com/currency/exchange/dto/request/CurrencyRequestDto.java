package com.currency.exchange.dto.request;

import com.currency.exchange.exception.ValidationException;


public record CurrencyRequestDto(String code) {

    public CurrencyRequestDto {
        if (code == null || !code.matches("^/[A-Za-z]{3}$")) {
            throw new ValidationException("Currency code is required");
        }
        code = code.replace("/", "").toUpperCase();
    }

}
