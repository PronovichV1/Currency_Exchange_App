package com.currency.exchange.dao;

import com.currency.exchange.Utill.ConnectionManager;
import com.currency.exchange.exception.DataBaseException;
import com.currency.exchange.exception.ExchangeRateAlreadyExistException;
import com.currency.exchange.model.Currency;
import com.currency.exchange.model.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpl implements ExchangeRateDao{

    private final String SQL_QUERY_FINDAL_ALL = "SELECT " +
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
    private final String SQL_QUERY_FIND_SPECIFIC_BY_PAIR_OF_IDS = "SELECT " +
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
            "JOIN currencies tc ON er.target_currency_id = tc.id " +
            "WHERE bc.id = ? AND tc.id = ?";
    private final String SQL_QUERY_SAVE_EXCHANGE_RATE = "INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate) " +
            "VALUES (?, ?, ?)";
    private final String SQL_QUERY_PATCH_EXCHANGE_RATE = "UPDATE exchange_rates " +
            "SET rate = ? " +
            "WHERE id = ?";

    private final String SQL_QUERY_FIND_DIRECT_EXCHANGE_RATE_BY_PAIR_OF_CODES = "SELECT " +
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
            "JOIN currencies tc ON er.target_currency_id = tc.id " +
            "WHERE bc.code = ? AND tc.code = ?";





    @Override
    public Optional<ExchangeRate> findSpecificExchangeRate(int baseCurrency, int targetCurrency) {

        try(Connection connection = ConnectionManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_QUERY_FIND_SPECIFIC_BY_PAIR_OF_IDS)){
            ps.setInt(1, baseCurrency);
            ps.setInt(2, targetCurrency);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return Optional.of(getExchangeRate(rs));
            }
        } catch (SQLException sqlException) {
            throw new DataBaseException("Failed to fetch specific exchange rate from the database. SQL State: " + sqlException.getSQLState());
        }

        return Optional.empty();
    }

    @Override
    public Optional<ExchangeRate> patch(ExchangeRate exchangeRate, double rate) {
        try(Connection connection = ConnectionManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_QUERY_PATCH_EXCHANGE_RATE)){
            ps.setDouble(1, rate);
            ps.setInt(2, exchangeRate.id());
            ps.executeUpdate();
            return Optional.of(new ExchangeRate(exchangeRate.id(), exchangeRate.baseCurrency(), exchangeRate.targetCurrency(), rate));

        } catch (SQLException e) {
            throw new DataBaseException("Error: database is not found" + e.getSQLState());
        }
    }
    @Override
    public Optional<ExchangeRate> save(ExchangeRate exchangeRate) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_SAVE_EXCHANGE_RATE, Statement.RETURN_GENERATED_KEYS)){
            exists(exchangeRate);
            preparedStatement.setInt(1, exchangeRate.baseCurrency().id());
            preparedStatement.setInt(2, exchangeRate.targetCurrency().id());
            preparedStatement.setDouble(3, exchangeRate.rate());
            preparedStatement.executeUpdate();
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    return Optional.of(new ExchangeRate(id, exchangeRate.baseCurrency(), exchangeRate.targetCurrency(), exchangeRate.rate()));
                }
            }
        }catch (SQLException sqlException){
            throw new DataBaseException("Error: database is not found" + sqlException.getSQLState());
        }
        return Optional.empty();
    }
    @Override
    public List<ExchangeRate> findAll() {
        List<ExchangeRate> exchangeRatesList = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_QUERY_FINDAL_ALL)){
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
    @Override
    public void exists(ExchangeRate exchangeRate) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY_FIND_SPECIFIC_BY_PAIR_OF_IDS)){
            ps.setInt(1, exchangeRate.baseCurrency().id());
            ps.setInt(2, exchangeRate.targetCurrency().id());
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    throw new ExchangeRateAlreadyExistException("Exchange rate with this code already exists");
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Failed to connect to database");
        }
    }

    @Override
    public Optional<ExchangeRate> findDirectPair(String codeA, String codeB){
        Optional<ExchangeRate> directExchangePair = queryDatabase(SQL_QUERY_FIND_DIRECT_EXCHANGE_RATE_BY_PAIR_OF_CODES, codeA, codeB);
        return directExchangePair;

    }


    private Optional<ExchangeRate> queryDatabase(String sql, String firstCode, String secondCode){
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1, firstCode);
            ps.setString(2, secondCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return Optional.of(getExchangeRate(rs));
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error: database is not found" + e.getSQLState());
        }
        return Optional.empty();
    }
}
