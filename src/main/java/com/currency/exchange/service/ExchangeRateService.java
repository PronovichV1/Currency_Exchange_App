package com.currency.exchange.service;

import com.currency.exchange.dao.ExchangeRateDao;
import com.currency.exchange.dto.request.ExchangeRateRequestDto;
import com.currency.exchange.dto.request.ExchangeRatesRequestDto;
import com.currency.exchange.exception.ExchangeRateNotFoundException;
import com.currency.exchange.model.Currency;
import com.currency.exchange.model.ExchangeRate;

import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao;
    private final CurrencyService currencyService;

    public ExchangeRateService(ExchangeRateDao exchangeRateDao, CurrencyService currencyService) {
        this.exchangeRateDao = exchangeRateDao;
        this.currencyService = currencyService;
    }

    public List<ExchangeRate> findAll() {
        return exchangeRateDao.findAll();
    }

    public ExchangeRate findByCodePair(ExchangeRateRequestDto exchangeRateRequestDto) {
        String baseCurrencyCode = exchangeRateRequestDto.requestedCurrencies().substring(0, 3);
        String targetCurrencyCode = exchangeRateRequestDto.requestedCurrencies().substring(3);
        int baseCurrency = currencyService.findSpecific(baseCurrencyCode).id();
        int targetCurrency = currencyService.findSpecific(targetCurrencyCode).id();
        return exchangeRateDao.findSpecificExchangeRate(baseCurrency, targetCurrency).orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate does not exist"));
    }

    public ExchangeRate save(ExchangeRatesRequestDto exchangeRatesRequestDto) {
        Currency baseCurrency = currencyService.findSpecific(exchangeRatesRequestDto.baseCurrencyCode());
        Currency targetCurrency = currencyService.findSpecific(exchangeRatesRequestDto.targetCurrencyCode());
        ExchangeRate reqExchangeRate = new ExchangeRate(0, baseCurrency, targetCurrency, exchangeRatesRequestDto.rate());
        return exchangeRateDao.save(reqExchangeRate).orElseThrow();
    }


    public ExchangeRate updateRate(ExchangeRateRequestDto exchangeRateRequestDto, double rate) {
        ExchangeRate exchangeRate = findByCodePair(exchangeRateRequestDto);
        return exchangeRateDao.patch(exchangeRate, rate).orElseThrow();
    }


}
