package com.currency.exchange.dao;

import com.currency.exchange.Utill.ConnectionManager;
import com.currency.exchange.exception.DataBaseException;
import com.currency.exchange.model.Currency;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

public class CurrencyDaoImpl implements CurrencyDao{
    public final String SQL_QUERY_FIND_ALL_CURRENCIES = "SELECT * FROM currencies";

    @Override
    public Optional<Currency> findByCode(String code) {
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
