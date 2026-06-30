package com.currency.exchange.dao;

import com.currency.exchange.model.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateDao extends BaseDao<ExchangeRate>{
    Optional<ExchangeRate> findSpecificExchangeRate(int baseCurrency, int targetCurrency);
}
