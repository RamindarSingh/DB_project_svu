package com.svuproject;

import java.sql.*;
import java.util.Scanner;
public class SqlDriver {

    //  Database credentials
    static final String USER = "student";
    static final String PASS = "raminder1986";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("**************Hello everyone, this is G1 group******************");
            System.out.println("*                                                              *");
            System.out.println("*        Raminder Singh     - 1603010019                       *");
            System.out.println("*        Savita Pandey      - 1601010164                       *");
            System.out.println("*        Bharath kumar      - 1601010526                       *");
            System.out.println("*        Mangulal Dhanavath - 1601010505                       *");
            System.out.println("*        Faraz Ahmed        - 1503050035                       *");
            System.out.println("*        Nithin pinnamaneni - 150201595                        *");
            System.out.println("*        Mudasir ahmed      - 1503011901                       *");
            System.out.println("*        Mahender Kumar     - 1503012094                       *");
            System.out.println("*        J A K Mohammed     - 1601010496                       *");
            System.out.println("*                                                              *");
            System.out.println("****************************************************************");
            System.out.println("");
            System.out.println("");
            System.out.println("Should the Project create the Books Database....(Y/N)");

            Scanner sc = new Scanner(System.in);
            
            String input = sc.next();
            
            if(!input.equals("Y"))
            	return;
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost/", "student", "raminder1986");
            stmt = conn.createStatement();

            System.out.println("Database connection is all set");
     
            String sql = "CREATE DATABASE books";
            stmt.executeUpdate(sql);
            
            System.out.println("books Database created successfully...");

            // We establish a Connection to the database with JDBC
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/books" , "student" , "raminder1986");
            Statement myStmt = myConn.createStatement();

            // Using the Table Generator class to generate our tables
            CreateTables createTab= new CreateTables(myStmt,myConn);

            // Create and initialize the tables with random values
            createTab.createTable();
            createTab.autoPopulate();

            SqlResultSet queryPrinter = new SqlResultSet("jdbc:mysql://localhost/" , USER , PASS);

            ResultSet resultAuthors = queryPrinter.getResultSet("SELECT * FROM Books.authors ORDER BY last DESC");

            int rowCount = 0;
            
            while(resultAuthors.next()) {  
                int id = resultAuthors.getInt("authorID");
                String first = resultAuthors.getString("first");
                String last = resultAuthors.getString("last");
                System.out.println(id + ", " + first + ", " + last);
                ++rowCount;
            }
            

            ResultSet resultBooks = queryPrinter.getResultSet("SELECT * FROM Books.publishers");

            System.out.println("For the query: Select all publishers from the publishers table");
            System.out.println();
            System.out.println("The records selected are:");
            System.out.println();

            System.out.println("publisherID" + ", " + "publisherName");
            while(resultBooks.next()) { 
                int id = resultBooks.getInt("publisherID");
                String publisherName = resultBooks.getString("publisherName");
                System.out.println(id + ", " + publisherName);
                ++rowCount;
            }
            System.out.println();
            System.out.println();


            // Select a specific publisher and list all books published by that
            // publisher. Include the title, year and ISBN number. Order the information alphabetically by title.

            System.out.println("For the query: Select a specific publisher and list all books published by that\n" +
                    "publisher. Include the title, year and ISBN number. Order the information alphabetically by title");
            System.out.println();
            System.out.println("The records selected are:");
            System.out.println();
            for(int i =1; i<15; i++){
                ResultSet query3 = queryPrinter.getResultSet("SELECT isbn, title, copyright FROM books.titles WHERE publisherID ="+i+"");
                while(query3.next()){
                    System.out.println(query3.getString("isbn") + " , " +  query3.getString("title") + " , " +  query3.getString("copyright"));
                }

            }
            System.out.println();
            System.out.println();



            // Add new author

            queryPrinter.setResult("INSERT INTO books.authors (first, last) VALUES ('Shahbaz', 'Khan');");
            // Print out author table with the recently added Author
            System.out.println("For the query: Add new author");
            System.out.println();
            System.out.println("The records selected are:");
            System.out.println();

            ResultSet query4 = queryPrinter.getResultSet("SELECT * FROM Books.authors");

            System.out.println("For the addition: We print out authors table after the additon");
            System.out.println();
            System.out.println("The updated records are:");
            System.out.println();
            int rowCount1 = 0;
            System.out.println("AuthorID" + ", " + "First" + ", " + "Last");
            while(query4.next()) {   // Move the cursor to the next row
                int id = query4.getInt("authorID");
                String first = query4.getString("first");
                String last = query4.getString("last");
                System.out.println(id + ", " + first + ", " + last);
                ++rowCount1;
            }
            System.out.println();
            System.out.println();


            // Edit/Update the existing information about an author
            queryPrinter.setResult("UPDATE books.authors SET first = \"Johnny\" WHERE authorID = 1");

            // Print out Author table with updated value
            ResultSet query5 = queryPrinter.getResultSet("SELECT * FROM Books.authors");

            System.out.println("For the update: Edit/Update the existing information about an author");
            System.out.println();
            System.out.println("The updated records are:");
            System.out.println();
            int rowCount2 = 0;
            System.out.println("AuthorID" + ", " + "First" + ", " + "Last");
            while(query5.next()) {   // Move the cursor to the next row
                int id = query5.getInt("authorID");
                String first = query5.getString("first");
                String last = query5.getString("last");
                System.out.println(id + ", " + first + ", " + last);
                ++rowCount1;
            }
            System.out.println();
            System.out.println();


            // Add a new title for an author

            queryPrinter.setResult("INSERT INTO books.titles (isbn, title, editionNumber, price)\n" +
                    " VALUES (\"890809809\",\"Book\", \"1234\", \"50\");");

            System.out.println("For the update: Add a new title for an author");
            System.out.println();
            System.out.println("The updated records are:");
            System.out.println();
            System.out.println("The new title is now added");


            // Add new publisher

            queryPrinter.setResult("INSERT INTO books.publishers (publisherName) VALUES (\"Shahbaz\");");
            // Print out the publishers Table with the new publisher added.
            ResultSet query6 = queryPrinter.getResultSet("SELECT * FROM Books.publishers");

            System.out.println("For the addition: We print out publishers table after the addition");
            System.out.println();
            System.out.println("The updated records are:");
            System.out.println();


            System.out.println("publisherID" + ", " + "publisherName");
            while(query6.next()) {   // Move the cursor to the next row
                int id = query6.getInt("publisherID");
                String publisherName = query6.getString("publisherName");
                System.out.println(id + ", " + publisherName);
                ++rowCount;
            }
            System.out.println();
            System.out.println();


            // Edit/Update the existing information about a publishe
            queryPrinter.setResult("UPDATE books.publishers SET publisherName = \"Shahbazi\" WHERE publisherID = 1;");
            System.out.println("For the update: Edit/Update the existing information about an author");
            System.out.println();
            System.out.println("The updated records are:");
            System.out.println();
            // Print out the publishers table with the updated value
            ResultSet query7 = queryPrinter.getResultSet("SELECT * FROM Books.publishers");

            System.out.println("For the update: We print out publishers table after the addition");
            System.out.println();
            System.out.println("The updated records are:");
            System.out.println();


            System.out.println("publisherID" + ", " + "publisherName");
            while(query7.next()) {   // Move the cursor to the next row
                int id = query7.getInt("publisherID");
                String publisherName = query7.getString("publisherName");
                System.out.println(id + ", " + publisherName);
                ++rowCount;
            }



        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("System END");
    }
}

