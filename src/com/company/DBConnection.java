
package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final static String url = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";
    private final static String user = "root";
    private final static String password = "root";
    private Connection con;
    public DBConnection() throws SQLException {
        con = DriverManager.getConnection(url, user, password);
    }
    
    public Connection getConnection() {
        return con;
    }
    
}
