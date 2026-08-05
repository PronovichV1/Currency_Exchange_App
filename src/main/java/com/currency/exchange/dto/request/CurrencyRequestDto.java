package com.currency.exchange.dto.request;

import com.currency.exchange.exception.ValidationException;


public record CurrencyRequestDto(String code) {

    public CurrencyRequestDto {
        if (code == null || code.equals("/")) {
            throw new ValidationException("Currency code is required");
        }
        code = code.replace("/", "").toUpperCase();
    }

}
