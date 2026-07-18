package com.currency.exchange.dto.response;

import com.currency.exchange.model.Currency;

public record ExchangeResponseDto(Currency baseCurrency, Currency targetCurrency, double rate, double amount,
                                  double convertedAmount) {
}
