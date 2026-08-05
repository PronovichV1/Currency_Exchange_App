
package com.currency.exchange.listener;

import com.currency.exchange.util.ConnectionManager;
import com.currency.exchange.exception.DataBaseException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initDatabase();
    }

    private void initDatabase() {
        try (InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream("init.sql")) {
            if (inputStream == null) {
                throw new FileNotFoundException();
            }
            String sqlScript = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
            String[] sqlScriptArray = sqlScript.trim().split(";");

            try (Connection connection = ConnectionManager.getConnection();
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
