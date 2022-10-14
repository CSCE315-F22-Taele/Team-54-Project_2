/**
 * This class establishes the backend for connecting to the SQL database
 * and populating it with tables containing data to track Chick-Fil-A operations
 * @author Krishnan Prashanth
 */
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
    // static HashMap<String, Integer> primaryKeys = new HashMap<>();

    /**
     * Populates the tableFields hashmap
     */
    private static void populateFields()
    {
        // THIS FUNCTION NEEDS TO MODIFIED WHENEVER ANY CHANGES TO THE TABLES ARE BEING MADE OR ANY TABLES ARE ADDED TO THE DATABASE.
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

        String[] menu = new String[]{"menuid", "name", "price", "category", "ingredients"};
        tableFields.put("menu", menu);

        String[] temp = new String[]{"name", "price"};
        tableFields.put("temp", temp);
    }


    /**
     * Establishes connection with database and populates it with tables
     */
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


    /**
     * Generates a PreparedStatement object that embodies a SQL query and can then be executed on database
     * for which connection already exists.
     * @param  query               SQL query to be run on database.
     * @return       object that can then be executedo n database.
     */
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

    /**
     * outputs the list of objects in a format required for SQL queries.
     * If quotes == False then the output would look like (vals[0], vals[1], vals[2], ..., vals[n])
     * else the output would look like ('vals[0]', 'vals[1]', 'vals[2]', ..., 'vals[n]').
     * @param  vals                 values that need to be processed.
     * @param  quotes               Whether or not you want quotes surrounding each object in the list.
     * @return        Return formatted String
     */
    private static String QueryFormat(Object[] vals, boolean quotes)
    {
        String output = "(";
        String temp;
        for(Object x : vals)
        {
            temp = quotes ? '\''+x.toString()+'\'' : x.toString();
            output += temp + ", ";
        }
        output = output.substring(0, output.length()-2) + ')';
        return output;
    }


    /**
     * Checks to see if there is a record in database where value == record[fieldName].
     * @param  tableName               Table in the database which needs to be checked.
     * @param  fieldName               The field that value is searched against.
     * @param  value                   The value compared against each record.
     * @return           Whether or not record is found.
     */
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


    /**
     * Retrieves a value from the database.
     * @param  tableName               Table in the database which needs to be checked.
     * @param  fieldName               the field that value is searched against.
     * @param  value                   Value that is compared against each record.
     * @return           desired database value, null if nothing is found.
     */
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


    /**
     * Retrives n values from the database.
     * @param  tableName               table in the database to check.
     * @param  fieldName               Field in which to search for the value
     * @param  value                   Value to look for.
     * @param  n                       Number of values wanted.
     * @return           List of matches found to value.
     */
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
                    {
                        // System.out.pr
                        record.put(field, result.getString(++i));
                    }
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

    /**
     * Adds a value to a table
     * @param  tableName               Table in the database to add the value to.
     * @param  record                  Hashmap containing record to add. Keys represent the fields.
     * @return           True if successfully added, false if not.
     */
    static boolean addValue(String tableName, HashMap<String, String> record)
    {
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // Inserting a record into the table
            // INSERT INTO table_name (column1, column2, column3, ...)
            // VALUES (value1, value2, value3, ...);
            String fields = QueryFormat(record.keySet().toArray(), false);
            String vals = QueryFormat(record.values().toArray(), true);
            // System.out.println("Fields: " + fields);
            // System.out.println("Vals: " + vals);
            query =   String.format("INSERT INTO %s %s ", tableName, fields)
                    + String.format("VALUES %s ;", vals);
            stmt = createStatement(query);
            int result = stmt.executeUpdate();
            return (1 == result);
            // System.out.println("Result: "+ result);

        } catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return false;
    }


    /**
     * Adds n values to a given database table.
     * @param  tableName               Table to add values to.
     * @param  records                 The values to add. Keys represent the fields.
     * @param  n                       Number of values to be added.
     * @return           Array of true or false depending on successful or unsuccessful addition.
     */
    static boolean[] addNValues(String tableName, ArrayList<HashMap<String, String>> records, int n)
    {

        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();
        
        

        return null;
    }
    
    


    static String[][] tableView(String tableName)
    {
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value.
            query = String.format("SELECT * FROM %s ;", tableName);
            stmt = createStatement(query);
            ResultSet result = stmt.executeQuery();
            String[] fields = tableFields.get(tableName);
            ArrayList<ArrayList<String>> nRecords =  new ArrayList<ArrayList<String>>();
            while(result.next())
            {
                int i = 0;
                ArrayList<String> record = new ArrayList<>();
                for(String field : fields)
                    {
                        record.add(result.getString(++i));
                    }
                nRecords.add(record);
            }

            String[][] view = new String[nRecords.size()][nRecords.get(0).size()];
            for(int r = 0; r < view.length; ++r)
                for(int c = 0; c < view[0].length; ++c)
                    view[r][c] = nRecords.get(r).get(c);

            return view;
        } catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return null;
    }



    /**
     * Runs query for which on existing function does not exist.
     * @param  sqlQuery               The query to use as a string.
     * @return          object the contains the result of running the query.
     */
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
        // ArrayList<HashMap<String, String>> temp = getNValues("employees", "firstname", "Tom", 2);
        // for(HashMap<String, String> x : temp)
            // System.out.println(x);
        HashMap<String, String> temp = new HashMap<>();
        String[] keys = new String[]{"customerid", "firstname", "lastname"};
        String[] vals = new String[]{"19", "Estella", "Chen"};
        for(int i = 0; i < keys.length; ++i)
            temp.put(keys[i], vals[i]);

        System.out.println(addValue("customers", temp));
        // System.out.println(temp);

    }
}
