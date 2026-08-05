package com.currency.exchange.validator;

import com.currency.exchange.dto.request.ExchangeRequestDto;
import com.currency.exchange.exception.ValidationException;

public class ExchangeRequestValidator implements Validator<ExchangeRequestDto>{
    @Override
    public void validate(ExchangeRequestDto target) {
        if (target.from().length() != 3) {
            throw new ValidationException("Base currency code must be exactly 3 characters long");
        }

        for (Character c : target.from().toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Base currency code must contain letters only");
            }
        }

        if (target.to().length() != 3) {
            throw new ValidationException("Target currency code must be exactly 3 characters long");
        }

        for (Character c : target.to().toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Target currency code must contain letters only");
            }
        }
    }


}
