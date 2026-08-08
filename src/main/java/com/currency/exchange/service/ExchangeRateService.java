package com.currency.exchange.service;

import com.currency.exchange.dao.ExchangeRateDao;
import com.currency.exchange.dto.request.ExchangeRateRequestDto;
import com.currency.exchange.dto.request.ExchangeRatesRequestDto;
import com.currency.exchange.exception.ExchangeRateNotFoundException;
import com.currency.exchange.exception.ValidationException;
import com.currency.exchange.model.Currency;
import com.currency.exchange.model.ExchangeRate;

import java.math.BigDecimal;
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
        return exchangeRateDao.findByCodes(baseCurrencyCode, targetCurrencyCode).orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate does not exist"));
    }

    public ExchangeRate save(ExchangeRatesRequestDto exchangeRatesRequestDto) {
        Currency baseCurrency = currencyService.findSpecific(exchangeRatesRequestDto.baseCurrencyCode());
        Currency targetCurrency = currencyService.findSpecific(exchangeRatesRequestDto.targetCurrencyCode());
        if (baseCurrency.code().equalsIgnoreCase(targetCurrency.code())){
            throw new ValidationException("Base currency and target currency cannot be the same.");
        }
        ExchangeRate reqExchangeRate = new ExchangeRate(0, baseCurrency, targetCurrency, exchangeRatesRequestDto.rate());
        return exchangeRateDao.save(reqExchangeRate).orElseThrow(() -> new IllegalStateException("Failed to save currency"));
    }


    public ExchangeRate updateRate(ExchangeRateRequestDto exchangeRateRequestDto, BigDecimal rate) {
        if (rate.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValidationException("Rate value must be positive");
        }
        ExchangeRate exchangeRate = findByCodePair(exchangeRateRequestDto);
        return exchangeRateDao.patch(exchangeRate, rate).orElseThrow();
    }


}
