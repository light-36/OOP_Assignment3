package com.pardus.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections
 */
public class DatabaseConnection {
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootpss04";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    
    // Static initialization block to load the JDBC driver
    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get a connection to the database
     * 
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            Properties props = new Properties();
            props.setProperty("user", USERNAME);
            props.setProperty("password", PASSWORD);
            
            return DriverManager.getConnection(URL, props);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw e;
        }
    }
}
