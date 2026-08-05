package com.currency.exchange.dto.request;

import com.currency.exchange.exception.ValidationException;

public record CurrencyRequestForPostDto(String name, String code, String sign) {

    public CurrencyRequestForPostDto {
        if (name == null) {
            throw new ValidationException("Currency name is required");
        }

        if (code == null) {
            throw new ValidationException("Currency code is required");
        }

        if (sign == null) {
            throw new ValidationException("Currency sign is required");
        }
        code = code.toUpperCase();
    }

}
