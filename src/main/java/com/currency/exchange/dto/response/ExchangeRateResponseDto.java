package com.currency.exchange.dto.response;

public record ExchangeRateResponseDto(int id, CurrencyResponseDto baseCurrency, CurrencyResponseDto targetCurrency, double rate) {
}
