package com.flipkart.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (isValid()) {
                return connection;
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FlipFit_Database", "root", "");

                } catch (ClassNotFoundException | SQLException  e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean isValid() throws SQLException {
        // return false;
        return connection != null && !connection.isClosed();
    }
}