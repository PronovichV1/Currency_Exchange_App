package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;



public record CurrencyRequestDto(String code) implements BaseDto {
    @Override
    public void validate() {
        if(code == null){
            throw new InvalidFormatException("Please enter the currency code");
        }

        if (code.length() != 3){
            throw new InvalidFormatException("Code must be exactly 3 letters");
        }

        for(Character c: code.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Code must contain only letters");
            }
        }

        if (code.equals(String.format("[\\p{isCyrillic}]+"))){
            throw new InvalidFormatException("Code must contain only Cyrillic letters");
        }

    }
}
