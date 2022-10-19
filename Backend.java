/**
 * This class establishes the backend for connecting to the SQL database
 * and populating it with tables containing data to track Chick-Fil-A operations
 * @author Krishnan Prashanth
 */
import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    static HashMap<String, Double> intialInventory = loadRemaining(); 
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
        // System.out.println("Username" + myCredentials.user);
        // System.out.println("Password" + myCredentials.pswd);
        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, myCredentials.user, myCredentials.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            // System.exit(0);
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
            // System.exit(0);
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
            
            // System.out.println("Function :: isValue " + "Query :: " + query);
            ResultSet result = stmt.executeQuery();
            return result.next();

        } catch (Exception e) {
            // System.out.println("Function :: isValue " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
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
    static HashMap<String, String> getValue(String tableName, String fieldName, String value) {
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value.
            query = String.format("SELECT * FROM %s WHERE %s = \'%s\';", tableName, fieldName, value);
            stmt = createStatement(query);
            // System.out.println("Function :: getValue " + "Query :: " + query);
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
            System.err.println("Function :: isValue " + "Query :: " + query);
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
            System.err.println("Function :: addNvalues QUERY :: " + query);
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
            // System.out.println("Function :: addValue " + "Query :: " + query);
            int result = stmt.executeUpdate();
            return (1 == result);
            // System.out.println("Result: "+ result);

        } catch (Exception e) {
            System.err.println("Function :: addValue " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
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
        boolean[] bl = new boolean[records.size()];

        String query = "nothing";
        try {
            // Inserting a record into the table
            // INSERT INTO table_name (column1, column2, column3, ...)
            // VALUES (value1, value2, value3, ...);
            for (int i = 0; i < records.size(); i++) {
                bl[i] = addValue(tableName, records.get(i));
            }
            // String fields = QueryFormat(record.keySet().toArray(), false);
            // String vals = QueryFormat(record.values().toArray(), true);
            // // System.out.println("Fields: " + fields);
            // // System.out.println("Vals: " + vals);
            // query =   String.format("INSERT INTO %s %s ", tableName, fields)
            //         + String.format("VALUES %s ;", vals);
            // stmt = createStatement(query);
            // int result = stmt.executeUpdate();
            // return (1 == result);
            // // System.out.println("Result: "+ result);

        } catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
        return bl;
    }
    
    /**
     * Views a table given the name of the database table.
     * @param tableName         Table to view.
     * @return                  Runs the query that returns the database table.
     */
    static String[][] tableView(String tableName)
    {
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value.
            query = String.format("SELECT * FROM %s;", tableName);
            stmt = createStatement(query);
            // System.out.println("Function :: tableView " + "Query :: " + query);
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

            Collections.sort(nRecords, new Comparator<ArrayList<String>> () {
                @Override
                public int compare(ArrayList<String> a, ArrayList<String> b) {
                    if(a.get(0) == null) return -1;
                    if(b.get(0) == null) return  1;

                    int id1 = Integer.parseInt(a.get(0));
                    int id2 = Integer.parseInt(b.get(0));
                    return id1-id2;
                }
            });
            // int pid = 0;
            String[][] view = new String[nRecords.size()][nRecords.get(0).size()];
            for(int r = 0; r < view.length; ++r)
            {
                for(int c = 0; c < view[0].length; ++c)
                    view[r][c] = nRecords.get(r).get(c);
            }
            return view;
        } catch (Exception e) {
            // System.out.println("Function :: tableView " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
        return null;
    }

    /**
     * Given a time window, displays the sales by item from the order history.
     * @param date1     Starting date.
     * @param date2     End date.
     * @return          Runs the query that returns the database table that displays the sales in a certain time.
     */
    static String[][] salesView(String date1, String date2)
    {
        // date1 < date2
        // date format: YYYY-MM-DD
        // This only for the order history
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();
        String tableName = "orders";
        String query = "nothing";
        try {
            // The Query to check if a record exists between date1 and date2.
            // SELECT * FROM orders WHERE saledate BETWEEN
            // '2022-10-01 00:00:00' AND '2022-11-10 12:00:00';
            query  = String.format("SELECT * FROM %s WHERE saledate BETWEEN ", tableName);
            query += String.format("\'%s 00:00:00\' AND \'%s 12:00:00\';", date1, date2);
            stmt = createStatement(query);
            // System.out.println("Function :: tableView " + "Query :: " + query);
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

            Collections.sort(nRecords, new Comparator<ArrayList<String>> () {
                @Override
                public int compare(ArrayList<String> a, ArrayList<String> b) {
                    if(a.get(0) == null) return -1;
                    if(b.get(0) == null) return  1;

                    int id1 = Integer.parseInt(a.get(0));
                    int id2 = Integer.parseInt(b.get(0));
                    return id1-id2;
                }
            });

            String[][] view = new String[nRecords.size()][nRecords.get(0).size()];
            for(int r = 0; r < view.length; ++r)
            {
                for(int c = 0; c < view[0].length; ++c)
                    view[r][c] = nRecords.get(r).get(c);
            }
            return view;
        } catch (Exception e) {
            // System.out.println("Function :: salesView " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
        return null;
    }
    
    /**
     * Given a timestamp, displays the list of items that only sold less than 10% of their inventory
     * between the timestamp and the current time. This assumes that no restocks have been done during that time.
     * @return a 2D list of Strings containing the name, category, quantity, and units of any items stored in excess
     */
    static String[][] excessView()
    {   
        // Need to show the name, category, quantity, and units for the items remaining. 
        //[]{"itemid", "name", "category", "expirationdate", "fridgerequired", "quantity", "unit"};
        // Indices Wanted: 1, 2, 5, 6 
        // String query = "nothing";
        try {
            HashMap<String, Double> currInventory = loadRemaining();
            ArrayList<String[]> notUsed = new ArrayList<String[]>();
            Object[] inventoryItems = intialInventory.keySet().toArray();

            for(Object item : inventoryItems)
            {
                String itemName = (String) item;
                double curr = currInventory.get(itemName);
                double init = intialInventory.get(itemName);
                
                if(curr/init > 0.9)
                {
                    HashMap<String, String> t = getValue("inventory", "name", "itemName");
                    notUsed.add(new String[]{t.get("name"), t.get("category"), t.get("quantity"), t.get("unit")});
                }

            }
            String[][] output = new String[notUsed.size()][4];
            int i = 0;
            for(String[] vals : notUsed)
            {
                output[i][1] = vals[1]; // name
                output[i][2] = vals[2]; // category
                output[i][5] = vals[5]; // quantity
                output[i][6] = vals[6]; // unit
            }
            
            return output;

        } catch (Exception e) {
            // System.out.println("Function :: tableView " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
        return null;
    }

    /**
     * Displays the list of items that have less than 10% of their original stock remaining in the store inventory.
     * Assumes no restocks have been done since the initial inventory was populated.
     * @return a 2D list of Strings containing the name, category, quantity, and units of any items that need to be restocked
     */
    static HashMap<String, Double> loadRemaining()
    {
        HashMap<String, Double> var = new HashMap<>();
        // Name of HashMap that needs to be populated: intialInventory
        if(conn == null || stmt == null) createConnection();
        String[][] vals = tableView("inventory");
        //[]{"itemid", "name", "category", "expirationdate", "fridgerequired", "quantity", "unit"};
        // Need index 0 -> 5
        for(int i = 0; i < vals.length; ++i)
        {
            var.put(vals[i][1], Double.parseDouble(vals[i][5]));
        }
        return var;   
    }

    static String[][] restockView()
    {
        // date1 < date2
        // date format: YYYY-MM-DD
        // This only for the order history
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();
        String tableName = "inventory";
        String query = "nothing";
        try {
            // The Query to check records that are below a certain threshold;
            // []{"itemid", "name", "category", "expirationdate", "fridgerequired", "quantity", "unit"};
            query  = String.format("SELECT * FROM inventory WHERE", tableName);
            query += String.format("(unit='number' AND quantity<100) OR ", tableName);
            query += String.format("(unit='lbs' AND quantity<50) OR ", tableName);
            query += String.format("(unit='gal' AND quantity<1);", tableName);
            System.out.println("Function :: restockView " + "Query :: " + query);


            stmt = createStatement(query);
            // System.out.println("Function :: tableView " + "Query :: " + query);
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

            Collections.sort(nRecords, new Comparator<ArrayList<String>> () {
                @Override
                public int compare(ArrayList<String> a, ArrayList<String> b) {
                    if(a.get(0) == null) return -1;
                    if(b.get(0) == null) return  1;

                    int id1 = Integer.parseInt(a.get(0));
                    int id2 = Integer.parseInt(b.get(0));
                    return id1-id2;
                }
            });

            String[][] view = new String[nRecords.size()][nRecords.get(0).size()];
            for(int r = 0; r < view.length; ++r)
            {
                for(int c = 0; c < view[0].length; ++c)
                    view[r][c] = nRecords.get(r).get(c);
            }
            return view;
        } catch (Exception e) {
            // System.out.println("Function :: salesView " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
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

    /**
     * Returns the size of the specified SQL table
     * @param tableName the name to SQL table we want the size of
     * @return the size of the SQL table named tableName
     */
    static int getSize(String tableName)
    {
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value.
            // HashMap<String, String> record = new HashMap<>();
            query = String.format("SELECT COUNT(*) FROM %s;", tableName);
            stmt = createStatement(query);
            ResultSet result = stmt.executeQuery();
            if(result.next()) {return result.getInt(1);}
            
            // System.out.println("Function :: getSize QUERY :: " + query);
            
            } catch (Exception e) {
                // System.err.println("Function :: getSize QUERY :: " + query);
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                // System.exit(0);
            }
        return -1;
    }

    /**
     * Updates the specified table at the specified row and column with a specified value.
     * @param tableName the name of the table to update
     * @param row the row number of the table to update
     * @param col the column number the table to update
     * @param colName the name of the column of the cell that needs to be updated
     * @param data the data to put in the table at the specified row and column
     * @return whether or not the table was successfully updated
     */
    static boolean editTable(String tableName, int row, int col, String colName, Object data)
    {
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value.
            query = String.format("UPDATE %s SET %s = \'%s\' WHERE %s = %d ;", tableName, colName, (String) data, tableFields.get(tableName)[0], row);
            stmt = createStatement(query);
            // System.out.println("Function :: editTable " + "Query :: " + query);
            int result = stmt.executeUpdate();
            return (1 == result);

        }catch (Exception e) {
            // System.err.println("Function :: editTable " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }

        return false;
    }

    /**
     * Removes a row from the specified table. Used when removing items from the inventory or menu.
     * @param tableName the name of the table from which to remove a row
     * @param row the row number to remove from the table
     */
    static void removeRecord(String tableName, int row)
    {
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();
        String query = "nothing";
        
        try {
            // Removing a record on the table.
            // DELETE FROM tableName WHERE idTitle=row;
            // Updating the records after it. 
            // update tableName
            // set customer_id = customer_id - 1
            // where customer_id > row;
            row += 1; // JTable is 0-indexed and SQL Table in 1-indexed.
            String idTitle = tableFields.get(tableName)[0];
            query =  String.format("DELETE FROM %s WHERE %s=%d;", tableName, idTitle, row);
            query += String.format("update %s set %s = %s - 1 where %s > %d;", tableName, idTitle, idTitle, idTitle, row);
            stmt = createStatement(query);
            // System.out.println("Function :: removeRecord " + "Query :: " + query);
            int result = stmt.executeUpdate();
            // System.out.println("Result: "+ result);

        } catch (Exception e) {
            // System.err.println("Function :: removeRecord " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
    }

    /**
     * Adds an empty row to the specified SQL table
     * @param tableName the table to which to add the new row
     */
    static void addEmptyCell(String tableName)
    {
        // INSERT INTO table (primary_key) VALUES (NULL);
        // If a connection to the query does not already exist, we need to create that connection.
        if(conn == null || stmt == null) createConnection();

        try {
            
            HashMap<String, String> record = new HashMap<>();
            if(tableName == "inventory")
            {
                // []{"itemid", "name", "category", "expirationdate", "fridgerequired", "quantity", "unit"}
                int size = getSize("inventory")+1;
                record.put("itemid", String.valueOf(size));
                record.put("name", "MenuItem"+size);
                record.put("category", "Meat");
                record.put("expirationdate", "3003-01-01");
                record.put("fridgerequired", "false");
                record.put("quantity", "-1");
                record.put("unit", "false");
            }
            
            if (tableName == "menu") {
                // []{"menuid", "name", "price", "category", "ingredients"};
                int size = getSize("menu")+1;
                record.put("menuid", String.valueOf(size));
                record.put("name", "MenuItem"+size);
                record.put("price", "-1.00");
                record.put("category", "Breakfast");
                record.put("ingredients", "{}");
            }

            addValue(tableName, record);
        } catch (Exception e) {
            // System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
        return ;


    }

    /**
     * Helper function for updateInventoryFromOrder. Depletes a specified amount of the given ingredient from
     * the store inventory.
     * @param ingredient the ingredient to deplete stock from
     * @param qty the amount of the ingredient to deplete from the inventory
     */
    static void depleteInventory(String ingredient, Double qty)
    {
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            String tableName = "inventory";
            
            HashMap<String, String> temp = getValue(tableName, tableFields.get(tableName)[1], ingredient);

            // if(temp == null || temp.get("quantity") == null)
            //     return;
            double remaining = Double.parseDouble(temp.get("quantity"));
            // System.out.println("Currently Depleting");
            if (remaining - qty < qty)
            {
                //[]{"itemid", "name", "category", "expirationdate", "fridgerequired", "quantity", "unit"};
                query = String.format("UPDATE %s SET quantity = %s WHERE name = \'%s\';", tableName, String.valueOf(remaining-qty), ingredient);
                stmt = createStatement(query);
                // System.out.println("Function :: depleteInventory " + "Query :: " + query);
                int result = stmt.executeUpdate();  
                // System.out.println("Depleted :: " + result);
            }
            else
            {
                query = String.format("UPDATE %s SET quantity = %s WHERE name = \'%s\';", tableName, String.valueOf(remaining+20*qty), ingredient);
                stmt = createStatement(query);
                int result = stmt.executeUpdate();  
                
                System.out.println("Restock completed");   
            }
        }catch (Exception e) {
            System.err.println("Function :: depleteInventory " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
    }

    /**
     * Primary function for depleting inventory based on a customer order. Looks at the customer order to determine
     * the menu items ordered, then maps to menu table to decide how much of which ingredients to deplete from the store
     * inventory. Calls depleteInventory on each ingredient used up by the order.
     * @param order the customer's order, specifying the specific menu items they chose.
     */
    static void updateInventoryFromOrder(String order)
    {
        if(conn == null || stmt == null) createConnection();
        System.out.println("Inside update inventory");
        String query = "nothing";
        try {
            String[] itemsOrdered = order.split(",");
            itemsOrdered[0] = itemsOrdered[0].substring(1);
            int cutoff = itemsOrdered[itemsOrdered.length-1].length()-2;
            itemsOrdered[itemsOrdered.length-1] = itemsOrdered[itemsOrdered.length-1].substring(0, cutoff+1);   
            
            for(int i = 0; i < itemsOrdered.length; ++i)
            {
                itemsOrdered[i] = itemsOrdered[i].replace("\"", "");
                // System.out.print(itemsOrdered[i] + "||");
                HashMap<String, Double> ingredientsUsed = menuItemIngredients(itemsOrdered[i]);
                if(ingredientsUsed == null || ingredientsUsed.size() == 0)
                    {
                        System.out.println("EXIT" + itemsOrdered[i]);
                        continue;
                    }
                Object[] vals = ingredientsUsed.keySet().toArray();
                for(Object ingrid : vals)
                {
                    depleteInventory((String)ingrid, ingredientsUsed.get(ingrid)); 
                }
            }    
            // System.out.println();
        }catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
    }

    /**
     * Looks at all of the past orders (orders that initially populate the database) and depletes the base inventory.
     * This is what creates the "original inventory" used to calculate excess and restock reports.
     * Only called when setting up the database for the first time.
     */
    static void invDepletionInitial()
    {
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            String[][] orders = tableView("orders");
            int index = orders[0].length-1;
            System.out.println("Orders::"+orders.length);
            for(int o = 0; o < orders.length/10; ++o)
            {
                System.out.println(o);
                String[] itemsOrdered = orders[o][index].split(",");
                itemsOrdered[0] = itemsOrdered[0].substring(2);
                int cutoff = itemsOrdered[itemsOrdered.length-1].length()-2;
                itemsOrdered[itemsOrdered.length-1] = itemsOrdered[itemsOrdered.length-1].substring(0, cutoff);   
                
                for(int i = 0; i < itemsOrdered.length; ++i)
                {
                    itemsOrdered[i] = itemsOrdered[i].replace("\"", "");
                    // System.out.print(itemsOrdered[i] + "");
                    HashMap<String, Double> ingredientsUsed = menuItemIngredients(itemsOrdered[i]);
                    if(ingredientsUsed == null || ingredientsUsed.size() == 0)
                        continue;
                    Object[] vals = ingredientsUsed.keySet().toArray();
                    for(Object ingrid : vals)
                    {
                        System.out.println(ingrid + "--" +ingredientsUsed.get(ingrid));
                        depleteInventory((String)ingrid, ingredientsUsed.get(ingrid)); 
                    }
                }    
                // System.out.println();
            }
        }catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Helper function used in inventory depletion. Given a menu item, returns a list of ingredients 
     * used to create that menu item.
     * @param menuItem the menu item for which to return ingredients
     * @return a HashMap of ingredients used to create the given menu item, along with amounts of each ingredient used
     */
    static HashMap<String, Double> menuItemIngredients(String menuItem)
    {
        if(conn == null || stmt == null) createConnection();
        HashMap<String, Double> ingredients = new HashMap<>();
        String query = "nothing";
        try {
            // The Query is to get how much in ingredients was used for a specfic order.
            HashMap<String, String> temp = getValue("menu", "name", menuItem.trim());
            String val = ""; 
            String[] vals = new String[]{};
            if(temp != null)
            {
                val = temp.get("ingredients");
                vals = val.substring(1,val.length()-1).replace("\"", "").split(",");
            }
            for(String v : vals)
            {
                // System.out.println(v);
                int index = v.lastIndexOf(" ");
                String item = v.substring(0, index);
                double qty = Double.parseDouble(v.substring(index+1));
                // System.out.println("item :: " + item + " Qty :: " + qty);
                ingredients.put(item, qty);
            }
            if(ingredients.size() > 0)
                return ingredients;
            else
                return null;
        } catch (Exception e) {
            // System.out.println("Function :: menuItemIngredients " + "Query :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }

        return null;
    }  

    // Built for testing purposes. Should be commented out in the final version.
    public static void main(String args[])
    {
        // excessView();
        // restockView();
        invDepletionInitial();
        // depleteInventory("Pickles", 10.);
        // createConnection();
        // removeRecord("inventory", 0);
        // String[][] temp = salesView("2022-10-01", "2022-10-10");
        // System.out.println(temp.length);
        // menuItemIngredients("Grilled Chicken Club Sandwich");
        // invDepletionInitial();
        
        // System.out.println(isValue("employees", "firstname","Tom")); // In Employees Table
        // System.out.println(isValue("employees", "lastname","Quincy")); // In Employees Table
        // System.out.println(isValue("employees", "firstname","Grace")); // Not in Employees Table
        // System.out.println(isValue("employees", "lastname","George")); // In Employees Table
        // HashMap<String, String> temp = getValue("employees", "firstname", "Tom");
        // ArrayList<HashMap<String, String>> temp = getNValues("employees", "firstname", "Tom", 2);
        // for(HashMap<String, String> x : temp)
            // System.out.println(x);
        // HashMap<String, String> temp = new HashMap<>();
        // String[] keys = new String[]{"customerid", "firstname", "lastname"};
        // String[] vals = new String[]{"19", "Estella", "Chen"};
        // for(int i = 0; i < keys.length; ++i)
            // temp.put(keys[i], vals[i]);
        // System.out.println(addValue("customers", temp));
        // System.out.println(temp);
        // getSize("inventory");

    }

    /**
     * Drops the specified table from the SQL database. Called in the useDatabase.java file, during database
     * population.
     * @param tableName the name of the table to drop
     */
    public static void dropTable(String tableName) {

        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value.
            query = String.format("DROP TABLE %s CASCADE;", tableName);
            stmt = createStatement(query);
            System.err.println("QUERY :: " + query);
            stmt.executeUpdate();

        }catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
    }

    /**
     * Called in the useDatabase file to populate the SQL database. Creates a table in the database
     * with the given name.
     * @param tableName the name of the table to create
     */
    public static void createTable(String tableName) {
        if(conn == null || stmt == null) createConnection();

        String query = "nothing";
        try {
            // The Query to check if a record exists within the table where fieldName = value.
            String menuName = tableFields.get("temp")[0];
            String price = tableFields.get("temp")[1];
            query = String.format("CREATE TABLE %s (%s text, %s float);", tableName, menuName, price);
            stmt = createStatement(query);
            System.err.println("QUERY :: " + query);
            stmt.executeUpdate();

        }catch (Exception e) {
            System.err.println("QUERY :: " + query);
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            // System.exit(0);
        }
    }
}