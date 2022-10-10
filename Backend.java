import java.sql.*;
import java.io.*;  
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.String.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Collections.*;
import java.util.List;

public class Backend {
    
    static Connection conn; // This is the connection that will be utilized by the rest of the Java Connections. 
    static PreparedStatement stmt;
    static HashMap<String, String[]> tableFields = new HashMap<>();

    // THIS FUNCTION NEEDS TO MODIFIED WHENEVER ANY CHANGES TO THE TABLES ARE BEING MADE OR ANY TABLES ARE ADDED TO THE DATABASE.      
    private static void populateFields()
    {
        String[] customers = new String[]{"customerid", "firstname", "lastname"};
        tableFields.put("customers", customers);

        String[] employees = new String[]{"employeeid", "firstname", "lastname", "payrate", "role", "startdate", "ismanager"};
        tableFields.put("employees", employees);

        String[] finances = new String[]{"transactionid", "isdebit", "iscredit", "details", "amount"};
        tableFields.put("finances", finances);

        String[] inventory = new String[]{"itemid", "name", "category", "expirationdate", "fridgerequired", "quantity", "unit"};
        tableFields.put("inventory", inventory);

        String[] orders = new String[]{"orderid", "ordernumber", "totalprice", "saledate", "employeeid", "customerid", "satisfied", "itemsordered"};
        tableFields.put("orders", orders);
    }

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

        populateFields(); // Since a connection is only established once, we can populate all fields once.
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

    static HashMap<String, String> getValue(String tableName, String fieldName, String value)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();
        
        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value. 
            query = String.format("SELECT * FROM %s WHERE %s = \'%s\';", tableName, fieldName, value);
            stmt = createStatement(query);
            ResultSet result = stmt.executeQuery();
            HashMap<String, String> record = new HashMap<>();
            String[] fields = tableFields.get(tableName);
            int i = 0;
            if(result.next())
            {
                for(String field : fields)
                    record.put(field, result.getString(++i));
            }

            return record;
            

        } catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        return null;
    }

    static ArrayList<HashMap<String, String>> getNValues(String tableName, String fieldName, String value, int n)
    {
        // If a connection to the query does not already exist, we need to create that connection. 
        if(conn == null || stmt == null) createConnection();
        
        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value. 
            query = String.format("SELECT * FROM %s WHERE %s = \'%s\' LIMIT %d;", tableName, fieldName, value, n);
            stmt = createStatement(query);
            ResultSet result = stmt.executeQuery();
            String[] fields = tableFields.get(tableName);
            ArrayList<HashMap<String, String>> nRecords =  new ArrayList<HashMap<String, String>>();
            while(result.next())
            {
                int i = 0;
                HashMap<String, String> record = new HashMap<>();
                for(String field : fields)
                    record.put(field, result.getString(++i));
                nRecords.add(record);
            }

            return nRecords;
        } catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
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
        // HashMap<String, String> temp = getValue("employees", "firstname", "Tom");
        ArrayList<HashMap<String, String>> temp = getNValues("employees", "firstname", "Tom", 2);
        for(HashMap<String, String> x : temp)
            System.out.println(x);
        // System.out.println(temp);

    }
}
