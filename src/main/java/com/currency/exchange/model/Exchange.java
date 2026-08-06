package com.currency.exchange.model;

import java.math.BigDecimal;

public record Exchange(Currency baseCurrency, Currency targetCurrency, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
}
