
package com.currency.exchange.listener;

import com.currency.exchange.exception.DataBaseException;
import com.currency.exchange.util.DataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;


public class DatabaseInitializer {


    public void runScript(DataSource dataSource) {

        try (InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream("init.sql")) {
            if (inputStream == null) {
                throw new FileNotFoundException();
            }


            String sqlScript = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
            String[] sqlScriptArray = sqlScript.trim().split(";");

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                for (String line : sqlScriptArray) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    statement.executeUpdate(line);
                }
            } catch (SQLException e) {
                throw new DataBaseException("Failed to execute database initialization script", e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
