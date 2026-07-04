package com.currency.exchange.dto.response;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record ErrorResponseDto(String message) implements BaseDto {
    @Override
    public void validate() {

    }
}
