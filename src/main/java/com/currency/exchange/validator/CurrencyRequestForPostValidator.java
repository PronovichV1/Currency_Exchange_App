package com.currency.exchange.validator;

import com.currency.exchange.dto.request.CurrencyRequestForPostDto;
import com.currency.exchange.exception.ValidationException;

public class CurrencyRequestForPostValidator implements Validator<CurrencyRequestForPostDto>{
    @Override
    public void validate(CurrencyRequestForPostDto target) {
        if (target.code().length() != 3) {
            throw new ValidationException("Currency code must be exactly 3 characters long");
        }

        for (Character c : target.code().toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Currency code must contain letters only");
            }
        }
    }
}
