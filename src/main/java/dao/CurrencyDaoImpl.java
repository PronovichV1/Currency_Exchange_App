package dao;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class CurrencyDaoImpl implements CurrencyDao{
    @Override
    public Optional<Currency> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Currency> save() {
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() {
        return List.of();
    }
}
