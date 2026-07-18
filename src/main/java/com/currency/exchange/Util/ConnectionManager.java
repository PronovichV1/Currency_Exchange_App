package com.currency.exchange.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static final String PATH_TO_DB = "jdbc:sqlite:E:/Projects/Currency_Exchange_App/src/main/resources/currencie_exchange.db";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(PATH_TO_DB);
    }

}
