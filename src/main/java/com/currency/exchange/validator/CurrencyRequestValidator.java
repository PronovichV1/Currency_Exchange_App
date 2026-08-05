package com.currency.exchange.validator;

import com.currency.exchange.dto.request.CurrencyRequestDto;
import com.currency.exchange.exception.ValidationException;

public class CurrencyRequestValidator implements Validator<CurrencyRequestDto>{

    @Override
    public void validate(CurrencyRequestDto dto) {
        if (dto.code().length() != 3) {
            throw new ValidationException("Currency code must be exactly 3 characters long");
        }
        if (!dto.code().matches("[A-Za-z]{3}")) {
            throw new ValidationException("Currency code must contain letters only");
        }
    }
}
