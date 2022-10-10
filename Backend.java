import java.sql.*;
import java.io.*;  
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Backend {
    
    static Connection conn; // This is the connection that will be utilized by the rest of the Java Connections. 
    static Statement stmt; 

    public static void createConnection() 
    {
        //Building the connection with your credentials
        String teamNumber = "54";
        String sectionNumber = "904";
        String dbName = "csce331_" + sectionNumber + "_" + teamNumber; 
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 

        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, myCredentials.user, myCredentials.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        try {
            // Create a statement object
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }


    boolean isValue(String tableName, String fieldName, String value)
    {
        
        return true;
    }

    String[] getValue(String tableName, String fieldName, String value)
    {
        return null;
    }

    String[][] getNValues(String tableName, String fieldName, String value)
    {
        return null;
    }

    boolean addValue(String tableName, String[] record)
    {
        return true;
    }


    boolean[] addNValues(String tableName, String[][] records)
    {
        return null;
    }
    
    ResultSet runQuery(String sqlQuery)
    {
        return null;
    }
    
    // Built for testing purposes. Should be commented out in the final version. 
    public static void main(String args[]) 
    {
        createConnection();

    }
}
