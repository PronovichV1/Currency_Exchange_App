package com.currency.exchange.service;


import com.currency.exchange.dao.CurrencyDao;
import com.currency.exchange.dao.CurrencyDaoImpl;
import com.currency.exchange.dto.response.CurrencyResponseDto;
import com.currency.exchange.exception.CurrencyNotFoundException;
import com.currency.exchange.model.Currency;
import java.util.List;
import java.util.Optional;

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
}
