package com.currency.exchange.service;

import com.currency.exchange.dao.ExchangeRateDao;
import com.currency.exchange.dto.reciept.ExchangeRequestDto;
import com.currency.exchange.model.Exchange;
import com.currency.exchange.model.ExchangeRate;

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
                .or(() -> exchangeByUsd(codeA, codeB, exchangeRequestDto)).orElseThrow(() -> new NoSuchElementException("Operation is not possible for these currencies"));
    }

    private Exchange getExchange(Optional<ExchangeRate> firstPair, Optional<ExchangeRate> secondPair, ExchangeRequestDto exchangeRequestDto) {
        ExchangeRate first = firstPair.get();
        ExchangeRate second = secondPair.get();
        double rate = second.rate() / first.rate();
        double amount = exchangeRequestDto.getParseAmount();
        double convertedAmount = rate * amount;
        return new Exchange(first.targetCurrency(), second.targetCurrency(), rate, amount, convertedAmount);
    }

    private Exchange getExchange(Optional<ExchangeRate> pairForExchange, ExchangeRequestDto exchangeRequestDto) {
        ExchangeRate exchangeRate = pairForExchange.get();
        double rate = exchangeRate.rate();
        double amount = exchangeRequestDto.getParseAmount();
        double convertedAmount = rate * amount;
        return new Exchange(exchangeRate.baseCurrency(), exchangeRate.targetCurrency(), rate, amount, convertedAmount);
    }


    private Optional<Exchange> tryDirectExchange(String from, String to, ExchangeRequestDto request) {
        Optional<ExchangeRate> directPair = exchangeRateDao.findDirectPair(from, to);
        if (directPair.isPresent()) {
            Exchange exchange = getExchange(directPair, request);
            return Optional.of(exchange);
        }
        return Optional.empty();

    }

    private Optional<Exchange> tryReverseExchange(String from, String to, ExchangeRequestDto request) {
        Optional<ExchangeRate> directPair = exchangeRateDao.findDirectPair(to, from);
        if (directPair.isPresent()) {
            Exchange exchange = getReverseExchange(directPair, request);
            return Optional.of(exchange);
        }
        return Optional.empty();
    }

    private Exchange getReverseExchange(Optional<ExchangeRate> directPair, ExchangeRequestDto request) {
        ExchangeRate direct = directPair.get();
        double rate = 1 / direct.rate();
        double amount = request.getParseAmount();
        double convertedAmount = rate * amount;
        return new Exchange(direct.targetCurrency(), direct.baseCurrency(), rate, amount, convertedAmount);

    }

    private Optional<Exchange> exchangeByUsd(String from, String to, ExchangeRequestDto request) {
        Optional<ExchangeRate> firstPair = exchangeRateDao.findDirectPair(USD, from);
        Optional<ExchangeRate> secondPair = exchangeRateDao.findDirectPair(USD, to);
        if (firstPair.isPresent() && secondPair.isPresent()) {
            return Optional.of(getExchange(firstPair, secondPair, request));
        }
        return Optional.empty();
    }
}
