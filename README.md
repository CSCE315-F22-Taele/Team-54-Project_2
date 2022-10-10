# Project2

This is the repository for CSCE 331 Team 54's Project 2.

Current stage: Phase 2, Database Correctness

The following are the backend functions and their respective purpose.
## Backend Breakdown:

### List of Methods Available

- `boolean isValue(String tableName, String fieldName, String value)`  checks to see if a value exists
-  `String[] getValue(String tableName, String fieldName, String value)` checks `fieldName` for all records and checks for the first record to match `value`.  
-  `String[][] getNValues(String tableName, String fieldName, String value)` checks `fieldName` and returns the first `N` values that match `value`. If `N` matches do not exist, it will return as many matches to the `value`. 
- `boolean addValue(String tableName, String[] record)` adds the record to `tableName` in our database.  The record needs to provide a list of arguments in the order that they exist within the table. Returns `True` or `False` depending on whether or not the record is successfully added or not. 
- `boolean[] addNValues(String tableName, String[][] records)` adds N records and calls upon `addValue()`. The format for each record needs to remain consistent with the table. And `bool[]` returns whether or not each record was successfully updated.
- `ResultSet runQuery(String sqlQuery)` is meant for running custom SQL queries that are too niche or uncommon to be added to the existing set of functions. This way, middleware or Event Listener methods can still get specific data values from the tables. The query result will be returned as a [ResultSet](https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html) and here is [how to parse it](https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html). 

If you have any other functions that you would like to have added, PLEASE LET ME KNOW! This will remain open until the end of the project!

### Pre-requisites to running any backend functions

In order to run any of the backend methods, you need to create a file in your local repo called `dbSetup.java`, that contains the following code:
```
public class dbSetup {
    String user = "csce331_904_<firstName>";
    String pswd = "<password>";
}
```

Replace `<firstName>` and `<password>` fields with your credentials. 