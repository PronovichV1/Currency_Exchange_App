package com.currency.exchange.dao;

import com.currency.exchange.exception.CurrencyAlreadyExistException;
import com.currency.exchange.exception.DataBaseException;
import com.currency.exchange.model.Currency;
import com.currency.exchange.util.DataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CurrencyDaoImpl implements CurrencyDao {
    private final DataSource dataSource;
    private static final String SQL_QUERY_FIND_ALL_CURRENCIES = "SELECT * FROM currencies";
    private static final String SQL_QUERY_FIND_CURRENCY_CODE = "SELECT * " +
            "FROM currencies " +
            "WHERE code = ?";
    private static final String SQL_QUERY_POST_CURRENCY = "INSERT INTO currencies(full_name, code, sign) VALUES(?, ?, ?)";

    public CurrencyDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_QUERY_FIND_CURRENCY_CODE)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(getCurrency(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DataBaseException("Failed to find currency by code from the database. SQL State: " + sqlException.getSQLState(), sqlException);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Currency> save(Currency currency) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_QUERY_POST_CURRENCY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, currency.name());
            ps.setString(2, currency.code());
            ps.setString(3, currency.sign());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return Optional.of(new Currency(id, currency.name(), currency.code(), currency.sign()));
                }
            }
        } catch (SQLException sqlException) {
            if (isUniqueConstraintViolation(sqlException)){
                throw new CurrencyAlreadyExistException("Currency with code '" + currency.code() + "' already exists");
            }

            throw new DataBaseException("Failed to save the currency to the the database. SQL State: " + sqlException.getSQLState(), sqlException);
        }
        return Optional.empty();
    }

    private boolean isUniqueConstraintViolation(SQLException sqlException) {
        if (sqlException == null){
            return false;
        }

        if(sqlException.getErrorCode() == 19 || "23505".equals(sqlException.getSQLState())){
            return true;
        }

        String message = sqlException.getMessage();
        return message != null || message.contains("UNIQUE");
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencyList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_QUERY_FIND_ALL_CURRENCIES);
            while (rs.next()) {
                currencyList.add(getCurrency(rs));
            }
        } catch (SQLException sqlException) {
            throw new DataBaseException("Failed to fetch all currencies from the database. SQL State: " + sqlException.getSQLState(), sqlException);
        }
        return currencyList;
    }

    private Currency getCurrency(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("full_name");
        String code = resultSet.getString("code");
        String sign = resultSet.getString("sign");
        return new Currency(id, name, code, sign);
    }
}
