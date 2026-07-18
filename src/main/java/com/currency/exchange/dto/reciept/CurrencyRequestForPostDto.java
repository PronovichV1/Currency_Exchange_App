package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record CurrencyRequestForPostDto(String name, String code, String sign) implements BaseDto {

    public CurrencyRequestForPostDto {
        if (name == null) {
            throw new InvalidFormatException("Currency name is required");
        }

        if (code == null) {
            throw new InvalidFormatException("Currency code is required");
        }

        if (sign == null) {
            throw new InvalidFormatException("Currency sign is required");
        }
        code = code.toUpperCase();
    }


    @Override
    public void validate() {

        if (code.length() != 3) {
            throw new InvalidFormatException("Currency code must be exactly 3 characters long");
        }

        for (Character c : code.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new InvalidFormatException("Currency code must contain letters only");
            }
        }

    }
}
