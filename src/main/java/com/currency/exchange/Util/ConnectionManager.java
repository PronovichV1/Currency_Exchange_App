package com.currency.exchange.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static final String DB_PATH = "/opt/tomcat/currency_exchange.db";
    public static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

}
