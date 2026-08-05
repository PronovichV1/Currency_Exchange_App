package com.currency.exchange.dto.response;

import com.currency.exchange.model.Currency;

public record ExchangeRateResponseDto(int id, CurrencyResponseDto baseCurrency, CurrencyResponseDto targetCurrency, double rate) {
}
