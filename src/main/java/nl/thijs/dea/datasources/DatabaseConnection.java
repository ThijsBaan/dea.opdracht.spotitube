package nl.thijs.dea.datasources;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConnection {
    private Connection connection = null;

    public DatabaseConnection() {
        connect();
    }

    public Connection getConnection() {
        return connection;
    }

    private void connect() {
        try {
            var properties = new Properties();
            properties.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("database.properties")));
            Class.forName(properties.getProperty("driver"));

            connection = DriverManager.getConnection(properties.getProperty("conncectionString"));
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("An error message has occurred: " + e);
        }
    }
}