package com.currency.exchange.service;

import com.currency.exchange.dao.CurrencyDao;
import com.currency.exchange.dao.ExchangeRateDao;
import com.currency.exchange.model.ExchangeRate;

import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao;
    private final CurrencyDao currencyDao;
    public ExchangeRateService(ExchangeRateDao exchangeRateDao, CurrencyDao currencyDao) {
        this.exchangeRateDao = exchangeRateDao;
        this.currencyDao = currencyDao;
    }

    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }
}
