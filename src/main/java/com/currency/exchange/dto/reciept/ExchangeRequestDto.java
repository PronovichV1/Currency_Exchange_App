package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record ExchangeRequestDto(String from, String to, String amount) implements BaseDto {
    public ExchangeRequestDto{
        if (from == null) {
            throw new InvalidFormatException("Please enter base currency code");
        }

        if (to == null) {
            throw new InvalidFormatException("Please enter target currency code");
        }
        try{
            if (amount == null) {
                throw new InvalidFormatException("Please enter the amount");
            }
            double parsedAmount = Double.parseDouble(amount);
            if (parsedAmount <= 0 ){
                throw new InvalidFormatException("Please enter positive amount");
            }
        }catch (NumberFormatException e){
            throw new InvalidFormatException("Amount must be a valid number");
        }

    }

    @Override
    public void validate() {
        if (from.length() != 3){
            throw new InvalidFormatException("Base code must be exactly 3 letters");
        }

        for(Character c: from.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Base code must contain only letters");
            }
        }

        if (to.length() != 3){
            throw new InvalidFormatException("Target code must be exactly 3 letters");
        }

        for(Character c: to.toCharArray()){
            if(!Character.isLetter(c)){
                throw new InvalidFormatException("Target code must contain only letters");
            }
        }
    }

    public double getParseAmount() {
        return Double.parseDouble(this.amount);
    }
}
