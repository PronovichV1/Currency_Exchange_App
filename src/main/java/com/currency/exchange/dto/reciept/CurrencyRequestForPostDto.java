package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record CurrencyRequestForPostDto(String name, String code, String sign) implements BaseDto {

    public CurrencyRequestForPostDto {
        if (name == null) {
            throw new InvalidFormatException("Please enter the currency name");
        }

        if (code == null) {
            throw new InvalidFormatException("Please enter the currency code");
        }

        if (sign == null) {
            throw new InvalidFormatException("Please enter the currency sign");
        }
        code = code.toUpperCase();
    }


    @Override
    public void validate() {

        if (code.length() != 3){
            throw new InvalidFormatException("Code must be exactly 3 letters");
        }

        for(Character c: code.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Code must contain only letters");
            }
        }

    }
}
