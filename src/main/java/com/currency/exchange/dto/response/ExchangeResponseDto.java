package com.currency.exchange.dto.response;

import com.currency.exchange.model.Currency;

import java.math.BigDecimal;

public record ExchangeResponseDto(CurrencyResponseDto baseCurrency, CurrencyResponseDto targetCurrency, BigDecimal rate, BigDecimal amount,
                                  BigDecimal convertedAmount) {
}
