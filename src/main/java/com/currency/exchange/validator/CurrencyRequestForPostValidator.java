package com.currency.exchange.validator;

import com.currency.exchange.dto.request.CurrencyRequestForPostDto;
import com.currency.exchange.exception.ValidationException;

public class CurrencyRequestForPostValidator implements Validator<CurrencyRequestForPostDto>{
    @Override
    public void validate(CurrencyRequestForPostDto target) {

        if (target.name().isEmpty() || target.name().isBlank()){
            throw new ValidationException("Currency name cannot be empty");
        }

        if (target.code().length() != 3) {
            throw new ValidationException("Currency code must be exactly 3 characters long");
        }

        for (Character c : target.code().toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Currency code must contain letters only");
            }
        }

        if (target.sign().isEmpty() || target.sign().isBlank()){
            throw new ValidationException("Sigh cannot be empty");
        }

        if (target.sign().length() > 3){
            throw new ValidationException("Sign cannot be longer then 3 letter");
        }
    }
}
