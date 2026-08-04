package com.currency.exchange.dto.request;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.ValidationException;

public record CurrencyRequestForPostDto(String name, String code, String sign) implements BaseDto {

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


    @Override
    public void validate() {

        if (code.length() != 3) {
            throw new ValidationException("Currency code must be exactly 3 characters long");
        }

        for (Character c : code.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Currency code must contain letters only");
            }
        }

    }
}
