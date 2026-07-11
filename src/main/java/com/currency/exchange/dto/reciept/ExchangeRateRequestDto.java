package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

import static java.util.Collections.replaceAll;

public record ExchangeRateRequestDto(String requestedCurrencies) implements BaseDto {
    public ExchangeRateRequestDto{

        if (requestedCurrencies == null || requestedCurrencies.equals("/")){
                throw new InvalidFormatException("Request is empty");
            }
        requestedCurrencies = requestedCurrencies.replace("/", "");
    }

    @Override
    public void validate() {
        if (requestedCurrencies.length() != 6){
            throw new InvalidFormatException("Request length is invalid.");
        }

        for(Character c: requestedCurrencies.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Code must contain only letters.");
            }
        }
    }
}
