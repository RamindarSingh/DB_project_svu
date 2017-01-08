package com.svuproject;

import java.sql.*;
import java.util.Random;

public class CreateTables {
    Statement stMt;
    Connection coNN;
    public CreateTables(Statement stmt, Connection conn) {
        stMt=stmt;
        coNN=conn;
    }
    
    // This function creates the Tables
    public void createTable(){

        String Table1="CREATE TABLE authors "
                + "(authorID INTEGER not NULL auto_increment, "
                + " first VARCHAR(255), "
                + " last VARCHAR(255), "
                + " PRIMARY KEY ( authorID ))";

        String Table4="CREATE TABLE authorISBN "
                + "(authorID INT, "
                + "isbn CHAR(10), "
                + "FOREIGN KEY ( authorID )REFERENCES authors( authorID ), "
                + "FOREIGN KEY ( isbn )REFERENCES titles( isbn ))";

        String Table2="CREATE TABLE publishers "
                + "(publisherID INTEGER not NULL auto_increment, "
                + " publisherName char(100), "
                + " PRIMARY KEY ( publisherID))";

        String Table3="CREATE TABLE titles "
                + "(isbn CHAR(10), "
                + " title VARCHAR(500), "
                + " editionNumber INTEGER, "
                + " copyright CHAR(4), "
                + " publisherID INTEGER, "
                + " price FLOAT, "
                + " PRIMARY KEY ( isbn ), "
                + " FOREIGN KEY ( publisherID )REFERENCES publishers( publisherID ))";
        try{
            // Executing the TABLE functions to add teh tables
            stMt.executeUpdate(Table1);
            stMt.executeUpdate(Table2);
            stMt.executeUpdate(Table3);
            stMt.executeUpdate(Table4);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    // A Random word generator.
    public static String[] generateRandomWords(int numberOfWords)
    {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i < numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }

    // This Function AutoPopulates the Tables.
    public void autoPopulate()
    {
        try {
            // Create Arrays of Random Words
            String[] first = generateRandomWords(17);
            String[] last = generateRandomWords(17);
            String[] titles = generateRandomWords(17);
            String[] publisherName = generateRandomWords(17);
            
            // Loop through 16 times and Auto fill all table values
            for (int i=1; i<16;i++)
            {
                String inAuthor = "INSERT INTO books.authors (authorID, first, last) VALUES (" + i+ ", '" + first[i] +"', '"+last[i]+"');";

                stMt.execute(inAuthor);

                String inPublisher = "INSERT INTO Books.publishers (publisherID, publisherName) VALUES (" + i + ", '" + publisherName[i] + "');";

                stMt.execute(inPublisher);

                String inTitles = "INSERT INTO Books.titles (isbn, title,publisherID, copyright) VALUES ('isbn" + i + "', '" + titles[i] + "', " +i +" , "+ (2000+i)+");";

                stMt.execute(inTitles);

                String inISBN = "INSERT INTO Books.authorISBN (authorID, isbn) VALUES (" + i + ", 'isbn" + i + "');";

                stMt.execute(inISBN);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
