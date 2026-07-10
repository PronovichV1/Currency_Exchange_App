package com.currency.exchange.dto.reciept;

import com.currency.exchange.exception.InvalidFormatException;

import static java.util.Collections.replaceAll;

public record ExchangeRateRequestDto(String requestedCurrencies) {
    public ExchangeRateRequestDto{
        try{

            if (requestedCurrencies == null || requestedCurrencies.equals("/")){
                throw new InvalidFormatException("Request is empty");
            }

            requestedCurrencies = requestedCurrencies.replace("/", "");

            if (requestedCurrencies.length() != 6){
                throw new InvalidFormatException("Request length is invalid.");
            }

            for(Character c: requestedCurrencies.toCharArray()){
                if(!Character.isLetter(c)){
                    throw new InvalidFormatException("Code must contain only letters.");
                }
            }
        }catch (InvalidFormatException invalidFormatException){
            throw new InvalidFormatException(invalidFormatException.getMessage());
        }
    }
}
