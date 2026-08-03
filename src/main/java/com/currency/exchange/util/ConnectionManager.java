package com.currency.exchange.util;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionManager {
    private static final String DB_URL = "jdbc:sqlite:";
    private static String dbPath;
    private static final String PROPERTY_NAME = "db.properties";
    private static final String RESOURCES_KEY = "db.path";


    private ConnectionManager(){
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        try {
            dbPath = loadPathFromProperties();
            dbPath = DB_URL + dbPath;
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbPath);
    }


    public static String loadPathFromProperties() {
        Properties properties = new Properties();
        String result;

        /// read db.properties
        try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream(PROPERTY_NAME)) {
            if (input == null){
                throw new RuntimeException("File " + PROPERTY_NAME + " doesn't found in resources!");
            }
            properties.load(input);
            result = properties.getProperty(RESOURCES_KEY);

            if (result != null && result.equals("${DB_PATH}")) {
                Properties envProps = new Properties();
                /// read .env
                try (FileInputStream inputStream = new FileInputStream(".env")){
                    envProps.load(inputStream);
                    result = envProps.getProperty("DB_PATH");
                } catch (IOException e){
                    result = System.getenv("DB_PATH");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading database configuration", e);
        }
        return result;
    }

    }
