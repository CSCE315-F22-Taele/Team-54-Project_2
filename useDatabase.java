/**
 * This function populates the team's SQL database with tables to track
 * store inventory, menu, customers, employees, order records, and finances.
 * Contains a function to parse CSV files to get table data, then establishes a 
 * connection to the remote database and builds tables in the main function.
 * Uses SQL, Util, and IO libraries.
 * 
 * @author Mohona Ghosh
 */
import java.sql.*;
import java.io.*;  
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

/*
 * Function to read in data from csv files and store in database
 * @return ArrayList of ArrayList of strings of parsed CSV data
 */
public class useDatabase {

    
    /** 
     * Opens CSV file passed by user and parses data delimiting by commas.
     * Returns a 2D ArrayList of Strings to represent the parsed data, intended
     * to convert to a SQL table in the main function.
     * @param filename
     * @return ArrayList<ArrayList<String>> of parsed data
     */
    public static ArrayList<ArrayList<String>> readCSVFileName(String filename)  {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<String> rows = new ArrayList<String>();
        
        Scanner sc;

        try {
            sc = new Scanner(new File(filename));

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                rows = new ArrayList<String> (Arrays.asList(line.split(",")));
                data.add(rows);
            }

            // data.remove(0); // removes title row from CSV
            data.remove(0); // removes attributes list
        sc.close();  //closes the scanner
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Function used specifically to parse Orders.csv and return an array of items ordered for each order
     * in the CSV file.
     * @param rawData one row in the CSV file, split by the comma delimiter
     * @return An array of everything after a certain number of elements grouped together
     */
    public static ArrayList<String> makeItemsArray(ArrayList<String> rawData, int startIdx) {
        ArrayList<String> parsedData = new ArrayList<String>();
        // boolean pastPrice = false;

        for (int i = startIdx; i < rawData.size(); i++) {
            String parsedString = rawData.get(i).substring((rawData.get(i).indexOf(39)) + 1, rawData.get(i).lastIndexOf(39));
            // System.out.println(parsedString);
            parsedData.add(parsedString);
        }

        // System.out.println(parsedData);

        return parsedData;
    }

    /**
     * Takes in the menu_items
     * @param rawData the comma-delimited row from the CSV
     */
    public static ArrayList<ArrayList<String>> makeIngredientsArray(String filename) {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<String> rows = new ArrayList<String>();
        
        Scanner sc;

        try {
            sc = new Scanner(new File(filename));
            // int linenum = 3;
            while(sc.hasNextLine()) {
                // linenum += 1;
                String line = sc.nextLine();
                // System.out.println("new Line :: " + line);
                rows = new ArrayList<String> (Arrays.asList(line.split(",")));
                String parse = rows.get(rows.size()-1).replace('|', ',');
                rows.set(rows.size()-1, "{"+parse+"}"); 
                // System.out.println(rows.toString());
                data.add(rows);
            }

            // data.remove(0); // removes title row from CSV
            data.remove(0); // removes attributes list
            sc.close();  //closes the scanner
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print("Done reading the file");
        return data;
    }

    /**
     * Drops specified tables from the SQL database, along with any dependencies
     * @param tableNames the list of table to drop
     * @param stmt the statement object to run the SQL query
     * @return whether or not the function was successful
     */
    static void dropTables(String[] tableNames, Statement stmt) {
        try {
            for (String table : tableNames) {
                String query = String.format("DROP TABLE %s CASCADE;", table);
                stmt.executeUpdate(query);
                // ResultSet result = stmt.executeQuery();
                // return result.next(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        // return false;
    }

    /**
     * Grants full privileges to each member of the team--use whenever 1+ tables are repopulated
     * @param tableName the name of the table to grant privileges to
     * @param users the users to grant privileges to--FIRST NAMES ONLY, in lowercase
     * @param stmt the SQL statement object that runs the query
     * @return whether or not the privileges were granted
     */
    static void grantPrivileges(String tableName, String[] users, Statement stmt) {
        try {
            for (String user : users) {
                String query = String.format("GRANT ALL PRIVILEGES ON %s TO csce331_904_%s", tableName, user);
                stmt.executeUpdate(query);
                // ResultSet result = stmt.executeQuery();
                // return result.next(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        // return false;
    }

  //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  public static void main(String args[]) {

    //Building the connection with your credentials
     Connection conn = null;
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

     // Populate database
     try {
       // Create a statement object
       Statement stmt = conn.createStatement();

       // List of users for privileges
       String[] users = {"krishnan", "estella", "neha"}; // edit depending on who is running this 
       String[] dropTables = {/*"customers", "employees", "menu", "orders", */"inventory", /*"finances"*/}; // edit depending on which tables you want to drop
       
       // Drop existing tables
        dropTables(dropTables, stmt);
        System.out.println ("Tables dropped successfully");

       // Create tables (create strings and execute)
        // // Customers
        // String createCustomerTable = "CREATE TABLE customers (customerID int, firstName text, lastName text);";
        // stmt.executeUpdate(createCustomerTable);
        // grantPrivileges("customers", users, stmt);
        // // System.out.println("Created customers table");

        // // Employees
        // String createEmployeeTable = "CREATE TABLE employees (employeeID int, firstName varchar, lastName varchar, payRate float, role varchar, startDate date, isManager boolean);";
        // stmt.executeUpdate(createEmployeeTable);
        // grantPrivileges("employees", users, stmt);
        // System.out.println("Created employees table");

        // Inventory
        String createInventoryTable = "CREATE TABLE inventory (itemID int, name text, category text, expirationDate date, fridgeRequired boolean, quantity float, unit text);";
        stmt.executeUpdate(createInventoryTable);
        grantPrivileges("inventory", users, stmt);
        System.out.println("Created inventory table");

        // // Finances
        // String createFinanceTable = "CREATE TABLE finances (transactionID int, isDebit boolean, isCredit boolean, details text, amount float);";
        // stmt.executeUpdate(createFinanceTable);
        // grantPrivileges("finances", users, stmt);
        // System.out.println("Created finances table");

        // // Orders
        // String createOrdersTable = "CREATE TABLE orders (orderID int, orderNumber int, totalPrice float, saleDate date, employeeID int, customerID int, satisfied boolean, itemsOrdered text[]);";
        // stmt.executeUpdate(createOrdersTable);
        // grantPrivileges("orders", users, stmt);
        // System.out.println("Created orders table");

        // // Menu
        // String createMenuTable = "CREATE TABLE menu (menuID int, name text, price float, category text, ingredients text[]);";
        // stmt.executeUpdate(createMenuTable);
        // grantPrivileges("menu", users, stmt);
        // System.out.println("Created menu table");

        // POPULATE TABLES IN DATABASE
        // // For Customers
        // ArrayList<ArrayList<String>> customerList = readCSVFileName("Customers.csv");
        // for (int i = 0; i < customerList.size(); i++) {
        //     String customerID = customerList.get(i).get(0);
        //     String firstName = customerList.get(i).get(1);
        //     String lastName = customerList.get(i).get(2);

        //     // Query String
        //     String query = String.format("INSERT INTO customers VALUES ('%s', '%s', '%s');", customerID, firstName, lastName);

        //     // Execute the query on the server
        //     stmt.executeUpdate(query);
        // }

        // System.out.println("Populated customers table");

        // // For Employees
        // ArrayList<ArrayList<String>> employeeList = readCSVFileName("Employees.csv");
        // for (int i = 0; i < employeeList.size(); i++) {
        //     String employeeID = employeeList.get(i).get(0);
        //     String firstName = employeeList.get(i).get(1);
        //     String lastName = employeeList.get(i).get(2);
        //     String payRate = employeeList.get(i).get(3); 
        //     String role = employeeList.get(i).get(4);
        //     String startDate = employeeList.get(i).get(5);
        //     String isManager = employeeList.get(i).get(6);

        //     // Query String
        //     String query = String.format("INSERT INTO employees VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');", employeeID, firstName, lastName, payRate, role, startDate, isManager);

        //     // Execute query
        //     stmt.executeUpdate(query);
        // }

        // System.out.println("Populated employees table");

        // For Inventory
        ArrayList<ArrayList<String>> inventoryList = readCSVFileName("Inventory.csv");
        for (int i = 0; i < inventoryList.size(); i++) {
            String itemID = inventoryList.get(i).get(0);
            String name = inventoryList.get(i).get(1);
            String category = inventoryList.get(i).get(2);
            String expDate = inventoryList.get(i).get(3);
            String needsFridge = inventoryList.get(i).get(4);
            String quantity = inventoryList.get(i).get(5);
            String unit = inventoryList.get(i).get(6);

            // Query String
            String query = String.format("INSERT INTO inventory VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');", itemID, name, category, expDate, needsFridge, quantity, unit);

            // Execute query
            stmt.executeUpdate(query);
        }

        System.out.println("Populated inventory table");

        // // For Finances
        // ArrayList<ArrayList<String>> financesList = readCSVFileName("Finances.csv");
        // for (int i = 0; i < financesList.size(); i++) {
        //     String tranID = financesList.get(i).get(0);
        //     String isDebit = financesList.get(i).get(1);
        //     String isCredit = financesList.get(i).get(2);
        //     String details = financesList.get(i).get(3);
        //     String amount = financesList.get(i).get(4);

        //     // Query String
        //     String query = String.format("INSERT INTO finances VALUES ('%s', '%s', '%s', '%s', '%s');", tranID, isDebit, isCredit, details, amount);

        //     // Execute query
        //     stmt.executeUpdate(query);
        // }

        // System.out.println("Populated finances table");

        // // For Orders
        // ArrayList<ArrayList<String>> ordersList = readCSVFileName("Orders.csv");
        // for (int i = 0; i < ordersList.size(); i++) {
        //     String orderID = ordersList.get(i).get(0);
        //     String orderNum = ordersList.get(i).get(1);
        //     String totPrice = ordersList.get(i).get(2);
        //     String date = ordersList.get(i).get(3);
        //     String empID = ordersList.get(i).get(4);
        //     String custID = ordersList.get(i).get(5);
        //     String satisfied = ordersList.get(i).get(6);

        //     // System.out.println(ordersList.get(i));

        //     String items = makeItemsArray(ordersList.get(i), 7).toString().replace('[', '{').replace(']', '}');

        //     // Query string
        //     String query = String.format("INSERT INTO orders VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", orderID, orderNum, totPrice, date, empID, custID, satisfied, items);

        //     // Execute query
        //     stmt.executeUpdate(query);

        // }

        // System.out.println("Populated orders table");

        // // For Menu
        // ArrayList<ArrayList<String>> menuList = makeIngredientsArray("Menu.csv");
        // for (int i = 0; i < menuList.size(); i++) {
        //     String menuID = menuList.get(i).get(0);
        //     String name = menuList.get(i).get(1);
        //     String price = menuList.get(i).get(2);
        //     String category = menuList.get(i).get(3);
        //     String ingredients = menuList.get(i).get(4);

        //     // Query string
        //     String query = String.format("INSERT INTO menu VALUES ('%s', '%s', '%s', '%s', '%s')", menuID, name, price, category, ingredients);

        //     // Execute query
        //     stmt.executeUpdate(query);
        // }

        // System.out.println("Populated menu table");

       System.out.println("--------------------Sucessfully Completed Database Construction--------------------");
   } catch (Exception e) {
       e.printStackTrace();
       System.err.println(e.getClass().getName() + ": " + e.getMessage());
       System.exit(0);
   }

    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }
  }
}
