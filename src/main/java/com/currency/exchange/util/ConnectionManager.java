package com.currency.exchange.util;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    public static String DB_URL = "jdbc:sqlite:";
    public static String dbPath;
    private static String PROPERTY_NAME = "db.properties";
    private static String RESOURCES_KEY = "db.path";



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
