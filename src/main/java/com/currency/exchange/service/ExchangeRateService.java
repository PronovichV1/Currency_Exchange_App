package com.currency.exchange.service;

import com.currency.exchange.dao.CurrencyDao;
import com.currency.exchange.dao.ExchangeRateDao;
import com.currency.exchange.dto.reciept.ExchangeRateRequestDto;
import com.currency.exchange.exception.ExchangeRateNotFoundException;
import com.currency.exchange.model.ExchangeRate;

import java.util.Currency;
import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao;
    private final CurrencyService currencyService;
    public ExchangeRateService(ExchangeRateDao exchangeRateDao, CurrencyService currencyService, CurrencyDao currencyDao) {
        this.exchangeRateDao = exchangeRateDao;
        this.currencyService = currencyService;
    }

    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }

    public ExchangeRate findByCodePair(ExchangeRateRequestDto exchangeRateRequestDto) {
        String baseCurrencyCode = exchangeRateRequestDto.requestedCurrencies().substring(0,3);
        String targetCurrencyCode = exchangeRateRequestDto.requestedCurrencies().substring(3);
        int baseCurrency = currencyService.findSpecific(baseCurrencyCode).id();
        int targetCurrency = currencyService.findSpecific(targetCurrencyCode).id();
        return exchangeRateDao.findSpecificExchangeRate(baseCurrency, targetCurrency).orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate is not exist"));
    }
}
