package dao;

import model.ExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpl implements ExchangeRateDao{
    @Override
    public Optional<ExchangeRate> findSpecificExchangeRate(int baseCurrency, int targetCurrency) {
        return Optional.empty();
    }

    @Override
    public Optional<ExchangeRate> save() {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() {
        return List.of();
    }
}
