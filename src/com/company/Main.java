package com.company;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)throws SQLException {
	 input();
    }

    private static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    private static void checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            System.out.println("The password matches.");
        else
            System.out.println("The password does not match.");
    }
    public static void input() throws SQLException {

        Scanner scan = new Scanner(System.in);
        System.out.println("1 for Register Data;2 for Login; 3 for Output Data from table; any other exit");
        int k = scan.nextInt();

        if (k == 1) {
            DBReg();

        } else if (k == 2) {
            DBLog();

        } else if(k==3){
            DBOutput();
        }
        else {
            System.out.println("System Exit");
            System.exit(0);
        }
    }
    public static void DBReg() throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scan.nextLine();
        System.out.println("Enter password:");
        String password = scan.nextLine();

        PreparedStatement stmt = null;
        DBConnection db = new DBConnection();
        try{
            Statement st = db.getConnection().createStatement();
            String sql="insert into login(username,password) values(?, ?)";
            stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password));
            stmt.execute();
            System.out.println("Register Successfully");
            input();
        } catch (SQLException se){
            System.out.println(se.getMessage());
        }
    }
    public static void DBLog() throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter login: ");
        String username = scan.nextLine();
        System.out.println("Enter password: ");
        String pass = scan.nextLine();
        DBConnection db = new DBConnection();
        String userquery = "select password from login where username ='" + username + "' ";
        Statement st = db.getConnection().createStatement();
        ResultSet rs = st.executeQuery(userquery);
        if (rs.next()) {
            String password = rs.getString("password");
            checkPass(pass, password);
            input();
            } else {
                System.out.print("Check your login or password");
            input();
            }
    }

    public static void DBOutput() throws SQLException {
        DBConnection db = new DBConnection();
        try {
            Statement st = db.getConnection().createStatement();
            String query = "SELECT * FROM login";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Data s = new Data();
                s.setId(rs.getInt("id"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                System.out.println(s);
            }
            input();
        } catch (SQLException ex) {
            System.out.println("Failed");
        }
    }




}
