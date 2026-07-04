package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record CurrencyRequestForPostDto(String name, String code, String sign) implements BaseDto {
    @Override
    public void validate() {
        if (name == null){
            throw new InvalidFormatException("Please enter the name");
        }

        if (code == null){
            throw new InvalidFormatException("Please enter the code");
        }

        if (sign == null){
            throw new InvalidFormatException("Please enter the sign");
        }

        if (name.equals(String.format("[\\p{isCyrillic}]+"))){
            throw new InvalidFormatException("Name must contain only Cyrillic letters");
        }

        if (code.length() != 3){
            throw new InvalidFormatException("Code must be exactly 3 letters");
        }

        for(Character c: code.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Code must contain only letters");
            }
        }

        if (!code.matches("[A-Za-z\\s]+")){
            throw new InvalidFormatException("Code must contain only Latin letters");
        }


    }
}
