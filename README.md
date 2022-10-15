# Project 2

This is the repository for CSCE 331 Team 54's Project 2. It contains code to implement a Chick-Fil-A POS System, usable by both cashiers and the store manager.

All store data is stored and maintained in a SQL database. The GUI and any interaction with the database is coded in Java.

---

#### Cashier Functionality:
- View store menu items and prices, sorted by categories
- Add quantities of menu items to an order and view updated order in real-time
- View total price of order, updating in real-time
- Finalize order and update SQL database

#### Manager Functionality:
- View complete store inventory and menu
- Add, edit, and remove inventory items
- View sale trends for a menu item for a specified window of time
- Add, edit, and remove menu items, with updates persisting on the cashier side
- View reports of excess and understocked inventory items

---

### To run:
1. Clone this repo locally
2. Compile all java files using the command `javac *.java`
3. Run Program!
        1. Run on Windows: `java -cp ".;postgresql-42.2.8.jar" LaunchPage`
        2. Run on Mac: `java -cp ".:postgresql-42.2.8.jar" LaunchPage`
