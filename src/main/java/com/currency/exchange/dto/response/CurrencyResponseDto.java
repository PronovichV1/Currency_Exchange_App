package com.currency.exchange.dto.response;

import com.currency.exchange.dto.Validatable;
import com.currency.exchange.exception.InvalidFormatException;

public record CurrencyResponseDto(int id, String name, String code, String sign) implements Validatable {
    @Override
    public void validate() throws InvalidFormatException {

    }
}
