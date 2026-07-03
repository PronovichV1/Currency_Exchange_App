package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.Validatable;
import com.currency.exchange.exception.InvalidFormatException;

public record CurrencyRequestForPostDto(String name, String code, String sign) implements Validatable {


    @Override
    public void validate() throws InvalidFormatException {

    }
}
