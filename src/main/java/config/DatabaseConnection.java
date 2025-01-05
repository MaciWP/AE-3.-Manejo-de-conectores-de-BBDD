package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static utils.Constants.*;

public final class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/concesionario";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(true);
        }
        return connection;
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println(DATABASE_CONNECTION_CLOSED);
            } catch (SQLException e) {
                System.err.println(String.format(DATABASE_CONNECTION_ERROR, e.getMessage()));
            }
        }
    }
}