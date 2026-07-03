package com.currency.exchange.dto.response;

import com.currency.exchange.dto.Validatable;
import com.currency.exchange.exception.InvalidFormatException;

public record ErrorResponseDto(String message) implements Validatable {
    @Override
    public void validate() throws InvalidFormatException {

    }
}
