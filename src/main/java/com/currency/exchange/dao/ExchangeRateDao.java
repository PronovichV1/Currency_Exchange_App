package com.currency.exchange.dao;

import com.currency.exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExchangeRateDao extends BaseDao<ExchangeRate> {
    Optional<ExchangeRate> findSpecificExchangeRate(int baseCurrency, int targetCurrency);

    Optional<ExchangeRate> patch(ExchangeRate exchangeRate, BigDecimal rate);


    Optional<ExchangeRate> findByCodes(String codeA, String codeB);
}
