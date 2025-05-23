package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYsql {

    private static Connection connection;

    private static void createConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12779237",
                        "sql12779237",
                        "a4FnIevNwL"
                );
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized ResultSet execute(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }

        if (query.trim().toUpperCase().startsWith("SELECT")) {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } else {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            return stmt.getGeneratedKeys();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }
}
