package com.currency.exchange.validator;

import com.currency.exchange.dto.request.ExchangeRateRequestDto;
import com.currency.exchange.exception.ValidationException;

public class ExchangeRateRequestValidator implements Validator<ExchangeRateRequestDto>{
    @Override
    public void validate(ExchangeRateRequestDto target) {
        if (target.requestedCurrencies().length() != 6) {
            throw new ValidationException("Currency pair must contain exactly 6 characters");
        }

        for (Character c : target.requestedCurrencies().toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Currency codes must contain letters only");
            }
        }
    }
}
