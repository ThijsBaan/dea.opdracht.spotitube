package nl.thijs.dea.datasources;

import java.sql.*;

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
            String userID = "sa";
            String password = "password123";

            String connectionUrl = "jdbc:sqlserver://localhost;databaseName=dea_spotitube";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl, userID, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error message has occurred: " + e);
        }
    }
}
