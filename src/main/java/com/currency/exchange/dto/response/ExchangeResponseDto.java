package com.currency.exchange.dto.response;

import com.currency.exchange.model.Currency;

public record ExchangeResponseDto(CurrencyResponseDto baseCurrency, CurrencyResponseDto targetCurrency, double rate, double amount,
                                  double convertedAmount) {
}
