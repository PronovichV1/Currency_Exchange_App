package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record ExchangeRatesRequestDto(String baseCurrencyCode, String targetCurrencyCode, Double rate) implements BaseDto {

    public ExchangeRatesRequestDto{
        if (baseCurrencyCode == null) {
            throw new InvalidFormatException("Please enter base currency code");
        }

        if (targetCurrencyCode == null) {
            throw new InvalidFormatException("Please enter target currency code");
        }

        if (rate == null) {
            throw new InvalidFormatException("Please enter the rate");
        }
}


    @Override
    public void validate() {
        if (baseCurrencyCode.length() != 3){
            throw new InvalidFormatException("Base code must be exactly 3 letters");
        }

        for(Character c: baseCurrencyCode.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Base code must contain only letters");
            }
        }

        if (targetCurrencyCode.length() != 3){
            throw new InvalidFormatException("Target code must be exactly 3 letters");
        }

        for(Character c: targetCurrencyCode.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Target code must contain only letters");
            }
        }

        if (rate <= 0) {
            throw new InvalidFormatException("Exchange rate must be positive");
        }
    }
}
