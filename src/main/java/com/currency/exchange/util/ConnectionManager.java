package com.currency.exchange.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static String DB_URL = "jdbc:sqlite:";
    public static String dbPath = System.getenv("DB_PATH");

    static {
        try {
            if(dbPath == null){
                dbPath = "E:/Projects/Currency_Exchange_App/src/main/resources/currency_exchange.db";
            }
            dbPath = DB_URL + dbPath;
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbPath);
    }

}
