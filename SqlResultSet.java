package com.svuproject;

import java.sql.*;
public class SqlResultSet {

    public static String JDBCURL;

    public static String user;

    public static String pass;

    public static ResultSet rset;

    public SqlResultSet(String url, String user, String pass) {
        this.JDBCURL = url;
        this.user = user;
        this.pass = pass;
    }

    public ResultSet getResultSet(String query) {
        try {
            Connection conn = DriverManager.getConnection(
                    JDBCURL, user, pass); 

            Statement stmt = conn.createStatement();


            rset = stmt.executeQuery(query);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rset;
    }

    public void setResult(String query) {
        try {
            Connection conn = DriverManager.getConnection(
                    JDBCURL, user, pass); 

            Statement stmt = conn.createStatement();

            stmt.execute(query);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
