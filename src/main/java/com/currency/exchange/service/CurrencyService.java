package com.currency.exchange.service;


import com.currency.exchange.dao.CurrencyDao;
import com.currency.exchange.exception.CurrencyNotFoundException;
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


    public Currency findSpecific(String code) {
        return currencyDao.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency with code '%s' not found", code)));
    }

    public Currency save(Currency currency) {
        return currencyDao.save(currency).orElseThrow(() -> new IllegalStateException("Failed to save currency"));
    }
}
