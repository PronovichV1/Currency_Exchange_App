package com.currency.exchange.validator;

import com.currency.exchange.dto.request.ExchangeRatesRequestDto;
import com.currency.exchange.exception.ValidationException;

import java.math.BigDecimal;

public class ExchangeRatesRequestValidator implements Validator<ExchangeRatesRequestDto>{
    @Override
    public void validate(ExchangeRatesRequestDto target) {
        if (target.baseCurrencyCode().length() != 3) {
            throw new ValidationException("Base currency code must be exactly 3 characters long");
        }

        for (Character c : target.baseCurrencyCode().toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Base currency code must contain letters only");
            }
        }

        if (target.targetCurrencyCode().length() != 3) {
            throw new ValidationException("Target currency code must be exactly 3 characters long");
        }

        for (Character c : target.targetCurrencyCode().toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Target currency code must contain letters only");
            }
        }
        try {
            BigDecimal rateBigDecimal = new BigDecimal(target.rate());
            if (rateBigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException("Exchange rate must be greater than zero");
            }
        }catch(NumberFormatException e){
            throw new ValidationException("Rate must be a valid number");
        }


    }
}
