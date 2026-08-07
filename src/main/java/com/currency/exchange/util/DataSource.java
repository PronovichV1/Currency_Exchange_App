package com.currency.exchange.util;

import com.currency.exchange.exception.DataBaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private static final String DB_URL = "jdbc:sqlite:";
    private static String dbPath;
    private static final String PROPERTY_NAME = "db.properties";
    private static final String RESOURCES_KEY = "db.path";

    public DataSource(){
        try {
            dbPath = loadPathFromProperties();
            dbPath = DB_URL + dbPath;
            config.setJdbcUrl(dbPath);
            config.setDriverClassName("org.sqlite.JDBC");

            ds = new HikariDataSource(config);
        }catch (Exception e){
            throw new DataBaseException("Failed to initialize HikariCP connection pool", e);
        }
    }


    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void close(){
        if (ds != null && !ds.isClosed()){
            ds.close();
        }
    }

    private String loadPathFromProperties() {
        Properties properties = new Properties();
        String result;

        /// read db.properties
        try (InputStream input = DataSource.class.getClassLoader().getResourceAsStream(PROPERTY_NAME)) {
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
