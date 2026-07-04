package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;



public record CurrencyRequestDto(String code) implements BaseDto {

    public CurrencyRequestDto {

        if (code == null || code.equals("/")) {
            throw new InvalidFormatException("Please enter the currency code");
        }
        code = code.replace("/", "").toUpperCase();
    }


    @Override
    public void validate() {


        if (code.length() != 3){
            throw new InvalidFormatException("Code must be exactly 3 letters");
        }


        if(!code.matches("[A-Za-z]{3}")){
                throw new InvalidFormatException("Code must contain only letters");
        }

    }
}
