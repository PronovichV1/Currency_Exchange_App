package com.currency.exchange.model;

public record Exchange(Currency baseCurrency, Currency targetCurrency, double rate, double amount,double convertedAmount) {
}
