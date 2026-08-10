package com.currency.exchange.dao;

import com.currency.exchange.model.Currency;

import java.util.Optional;

public interface CurrencyDao extends BaseDao<Currency> {
    Optional<Currency> findByCode(String code);
}
