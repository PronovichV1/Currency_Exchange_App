package com.currency.exchange.dto.response;

import com.currency.exchange.dto.BaseDto;

public record CurrencyResponseDto(int id, String code, String name, String sign) implements BaseDto {
    @Override
    public void validate() {}
}
