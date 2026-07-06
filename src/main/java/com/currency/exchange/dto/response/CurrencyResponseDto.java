package com.currency.exchange.dto.response;

import com.currency.exchange.dto.BaseDto;

public record CurrencyResponseDto(int id, String name, String code, String sign) implements BaseDto {
    @Override
    public void validate() {}
}
