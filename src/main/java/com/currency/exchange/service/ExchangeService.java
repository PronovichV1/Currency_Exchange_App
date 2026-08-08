package com.currency.exchange.service;

import com.currency.exchange.dao.ExchangeRateDao;
import com.currency.exchange.dto.request.ExchangeRequestDto;
import com.currency.exchange.exception.ExchangeRateNotFoundException;
import com.currency.exchange.model.Exchange;
import com.currency.exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ExchangeService {

    private final ExchangeRateDao exchangeRateDao;
    private static final String USD = "USD";

    public ExchangeService(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public Exchange findSpecific(ExchangeRequestDto exchangeRequestDto) {
        String codeA = exchangeRequestDto.from();
        String codeB = exchangeRequestDto.to();
        return tryDirectExchange(codeA, codeB, exchangeRequestDto)
                .or(() -> tryReverseExchange(codeA, codeB, exchangeRequestDto))
                .or(() -> exchangeByUsd(codeA, codeB, exchangeRequestDto)).orElseThrow(() -> new ExchangeRateNotFoundException("Operation is not possible for these currencies"));
    }

    private Exchange getExchange(Optional<ExchangeRate> firstPair, Optional<ExchangeRate> secondPair, ExchangeRequestDto exchangeRequestDto) {
        ExchangeRate first = firstPair.get();
        ExchangeRate second = secondPair.get();
        BigDecimal rate = second.rate().divide(first.rate(), 6, RoundingMode.HALF_UP);
        BigDecimal amount = new BigDecimal(exchangeRequestDto.amount());
        BigDecimal convertedAmount = rate.multiply(amount);
        return new Exchange(first.targetCurrency(), second.targetCurrency(), rate, amount, convertedAmount);
    }

    private Exchange getExchange(Optional<ExchangeRate> pairForExchange, ExchangeRequestDto exchangeRequestDto) {
        ExchangeRate exchangeRate = pairForExchange.get();
        BigDecimal rate = exchangeRate.rate();
        BigDecimal amount = new BigDecimal(exchangeRequestDto.amount());
        BigDecimal convertedAmount = rate.multiply(amount);
        return new Exchange(exchangeRate.baseCurrency(), exchangeRate.targetCurrency(), rate, amount, convertedAmount);
    }


    private Optional<Exchange> tryDirectExchange(String from, String to, ExchangeRequestDto request) {
        Optional<ExchangeRate> directPair = exchangeRateDao.findByCodes(from, to);
        if (directPair.isPresent()) {
            Exchange exchange = getExchange(directPair, request);
            return Optional.of(exchange);
        }
        return Optional.empty();

    }

    private Optional<Exchange> tryReverseExchange(String from, String to, ExchangeRequestDto request) {
        Optional<ExchangeRate> directPair = exchangeRateDao.findByCodes(to, from);
        if (directPair.isPresent()) {
            Exchange exchange = getReverseExchange(directPair, request);
            return Optional.of(exchange);
        }
        return Optional.empty();
    }

    private Exchange getReverseExchange(Optional<ExchangeRate> directPair, ExchangeRequestDto request) {
        ExchangeRate direct = directPair.get();
        BigDecimal rate = new BigDecimal("1").divide(direct.rate(), 6, RoundingMode.HALF_UP);
        BigDecimal amount = new BigDecimal(request.amount());
        BigDecimal convertedAmount = rate.multiply(amount);
        return new Exchange(direct.targetCurrency(), direct.baseCurrency(), rate, amount, convertedAmount);

    }

    private Optional<Exchange> exchangeByUsd(String from, String to, ExchangeRequestDto request) {
        Optional<ExchangeRate> firstPair = exchangeRateDao.findByCodes(USD, from);
        Optional<ExchangeRate> secondPair = exchangeRateDao.findByCodes(USD, to);
        if (firstPair.isPresent() && secondPair.isPresent()) {
            return Optional.of(getExchange(firstPair, secondPair, request));
        }
        return Optional.empty();
    }
}
