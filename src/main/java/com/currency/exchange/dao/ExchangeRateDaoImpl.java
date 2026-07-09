package com.currency.exchange.dao;

import com.currency.exchange.Utill.ConnectionManager;
import com.currency.exchange.exception.DataBaseException;
import com.currency.exchange.model.Currency;
import com.currency.exchange.model.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpl implements ExchangeRateDao{

    private String SQL_QUERY_FINAL_ALL = "SELECT " +
            "er.id," +
            "bc.id AS base_currency_id," +
            "bc.full_name," +
            "bc.code," +
            "bc.sign," +
            "tc.id AS target_currency_id," +
            "tc.full_name," +
            "tc.code," +
            "tc.sign," +
            "er.rate " +
            "FROM exchange_rates er " +
            "JOIN currencies bc ON er.base_currency_id = bc.id " +
            "JOIN currencies tc ON er.target_currency_id = tc.id";

    @Override
    public Optional<ExchangeRate> findSpecificExchangeRate(int baseCurrency, int targetCurrency) {
        return Optional.empty();
    }

    @Override
    public Optional<ExchangeRate> save(ExchangeRate Entity) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() {
        List<ExchangeRate> exchangeRatesList = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_QUERY_FINAL_ALL)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                exchangeRatesList.add(getExchangeRate(rs));
            }
        } catch (SQLException sqlException) {
            throw new DataBaseException("Failed to fetch all exchange rates from the database. SQL State: " + sqlException.getSQLState());
        }
        return exchangeRatesList;
    }


    public ExchangeRate getExchangeRate(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        Currency baseCurrency = new Currency(rs.getInt(2), rs.getString(3), rs.getString(4),rs.getString(5));
        Currency targetCurrency = new Currency(rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9));
        double rate = rs.getDouble(10);
        return new ExchangeRate(id, baseCurrency, targetCurrency, rate);
    }
}
