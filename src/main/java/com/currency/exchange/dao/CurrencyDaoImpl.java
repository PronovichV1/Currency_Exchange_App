package com.currency.exchange.dao;

import com.currency.exchange.Utill.ConnectionManager;
import com.currency.exchange.exception.CurrencyNotFoundException;
import com.currency.exchange.exception.DataBaseException;
import com.currency.exchange.model.Currency;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

public class CurrencyDaoImpl implements CurrencyDao{
    public final String SQL_QUERY_FIND_ALL_CURRENCIES = "SELECT * FROM currencies";
    public final String SQL_QUERY_FIND_CURRENCY_CODE = "SELECT * FROM currencies" +
            "WHERE code = ?";

    @Override
    public Optional<Currency> findByCode(String code) {
        List<Currency> currencies = new ArrayList<>();
        try(Connection connection = ConnectionManager.getConnection()){
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY_FIND_CURRENCY_CODE);
            ps.setString(1, code);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(getCurrency(rs));
                }
            }
        }catch (SQLException sqlException){
            throw new DataBaseException("Db error");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Currency> save(Currency Entity) {
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencyList = new ArrayList<>();
        try(Connection connection = ConnectionManager.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_QUERY_FIND_ALL_CURRENCIES);
            while (rs.next()){
                currencyList.add(getCurrency(rs));
            }

        }catch (SQLException sqlException){
            throw new DataBaseException("Db error");
        }
        return currencyList;
    }

    @Override
    public Currency getCurrency(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String full_name = resultSet.getString("full_name");
        String code = resultSet.getString("id");
        String sign = resultSet.getString("id");
        return new Currency(id, full_name, code, sign);
    }
}
