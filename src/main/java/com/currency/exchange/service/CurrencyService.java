package com.currency.exchange.service;


import com.currency.exchange.dao.CurrencyDao;
import com.currency.exchange.model.Currency;
import java.util.List;

public class CurrencyService {

    private final CurrencyDao currencyDao;

    public CurrencyService(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    public List<Currency> findAllCurrencies() {
        return currencyDao.findAll();
    }
}
