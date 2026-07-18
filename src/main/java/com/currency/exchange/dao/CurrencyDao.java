package com.currency.exchange.dao;

import com.currency.exchange.model.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface CurrencyDao extends BaseDao<Currency>{
    Optional<Currency> findByCode(String code);
    Currency getCurrency(ResultSet resultSet) throws SQLException;
    void exists(Currency currency);
}
