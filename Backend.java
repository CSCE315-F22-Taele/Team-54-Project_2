import java.sql.*;
import java.io.*;  
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.String.*;
import java.lang.reflect.Field;

public class Backend {
    
    static Connection conn; // This is the connection that will be utilized by the rest of the Java Connections. 
    static PreparedStatement stmt;

    private static void createConnection() 
    {
        //Building the connection with your credentials
        String teamNumber = "54";
        String sectionNumber = "904";
        String dbName = "csce331_" + sectionNumber + "_" + teamNumber; 
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        System.out.println("Username" + myCredentials.user);
        System.out.println("Password" + myCredentials.pswd);
        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, myCredentials.user, myCredentials.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
    }

    private static PreparedStatement createStatement(String query)
    {
        PreparedStatement temp;
        try {
            // Create a statement object
            temp = conn.prepareStatement(query);
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return null;
    }


    static boolean isValue(String tableName, String fieldName, String value)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();
        
        String query = "nothing";
        try {
        // The Query to check if a record exists within the table where fieldName = value. 
        query = String.format("SELECT * FROM %s WHERE %s = \'%s\';", tableName, fieldName, value);
        stmt = createStatement(query);
        ResultSet result = stmt.executeQuery();
        return result.next();

        } catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        return false;
    }

    static String[] getValue(String tableName, String fieldName, String value)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();

        return null;
    }

    static String[][] getNValues(String tableName, String fieldName, String value)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();

        return null;
    }

    static boolean addValue(String tableName, String[] record)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();

        return true;
    }


    static boolean[] addNValues(String tableName, String[][] records)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();

        return null;
    }
    
    static ResultSet runQuery(String sqlQuery)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();

        return null;
    }
    
    // Built for testing purposes. Should be commented out in the final version. 
    public static void main(String args[]) 
    {
        createConnection();
        // System.out.println(isValue("employees", "firstname","Tom")); // In Employees Table
        // System.out.println(isValue("employees", "lastname","Quincy")); // In Employees Table
        // System.out.println(isValue("employees", "firstname","Grace")); // Not in Employees Table
        // System.out.println(isValue("employees", "lastname","George")); // In Employees Table


        
        

    }
}
