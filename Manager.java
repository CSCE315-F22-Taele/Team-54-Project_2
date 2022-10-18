/**
 * This class implements the Manager GUI using the Swing and AWT libraries.
 * At a high level, the GUI lets the manager switch between different views that
 * allow them to view and edit inventory, view and edit the store menu, and 
 * view order trends with parameter filtration capacity.
 * 
 * @author Mohona Ghosh
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Manager implements ActionListener, TableModelListener {
    
    // Boilerplate Code
    private JFrame frame;
    private JToolBar tb;
    static JButton inventoryButton, menuButton, trendsButton, excessButton, restockButton;
    static JButton invAddButton, invRemoveButton, menAddButton, menRemoveButton;
    static JTextField saleStart, saleEnd;

    String saleStartDate = "2022-10-04";
    String saleEndDate = "2022-10-10";
    String[][] saleData = Backend.salesView(saleStartDate, saleEndDate);

    CardLayout cardLayout;
    JPanel cardPanel;

    private JButton backButton = new JButton("Go Back");

    Manager() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tb = new JToolBar();

        // create panel to hold function switches
        JPanel functionPanel = new JPanel();

        // create function switches
        inventoryButton = new JButton("Inventory");
        menuButton = new JButton("Menu Editor");
        trendsButton = new JButton("View Sales Report"); // NOTE: this button does not work yet (coming in Phase 4)

        functionPanel.add(backButton);
        functionPanel.add(inventoryButton);
        functionPanel.add(menuButton);
        functionPanel.add(trendsButton);
        functionPanel.add(excessButton);
        functionPanel.add(restockButton);

        inventoryButton.addActionListener(this);
        menuButton.addActionListener(this);
        trendsButton.addActionListener(this);
        backButton.addActionListener(this);
        excessButton.addActionListener(this);
        restockButton.addActionListener(this);

        // Setting up panel with the frame and displaying frame
        frame.add(functionPanel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);

        // make cards for switching logic and add actual material to each view
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(inventoryPanel(), "inventory");
        cardPanel.add(menuPanel(), "menu editor");
        cardPanel.add(trendsPanel(), "trends");
        cardPanel.add(excessPanel(), "excess");
        cardPanel.add(restockPanel(), "restock");

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(controlPanel(), BorderLayout.AFTER_LINE_ENDS);

        // set basic frame dimensions/characteristics
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Manager GUI");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    
    /** 
     * Calls functions based on which button is pressed by the user.
     * Overrides the actionPerformed method from the interface ActionListener.
     * @param e the action performed by the user
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inventoryButton) {
            cardLayout.show(cardPanel, "inventory");
        } else if (e.getSource() == menuButton) {
            cardLayout.show(cardPanel, "menu editor");
        } else if (e.getSource() == trendsButton) {
            cardLayout.show(cardPanel, "trends");
        } else if (e.getSource() == backButton) {
            frame.dispose();
            new LaunchPage();
        } else if (e.getSource() == invAddButton) {
            Backend.addEmptyCell("inventory");
            frame.dispose();
            new Manager();
        } else if (e.getSource() == menAddButton) {
            Backend.addEmptyCell("menu");
            frame.dispose();
            new Manager();
        } else if (e.getSource() == excessButton) {
            cardLayout.show(cardPanel, "excess");
        } else if (e.getSource() == restockButton) {
            cardLayout.show(cardPanel, "restock");
        }
        // remove buttons are in the functions that create their specific panels
    }

    
    /** 
     * Creates GUI view for the Manager to view inventory data and edit inventory records.
     * @return JPanel containing the inventory data with editing capabilities
     */
    private JPanel inventoryPanel() {
        // url: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
        // url: https://stackoverflow.com/questions/27815400/retrieving-data-from-jdbc-database-into-jtable
        
        String[][] data = Backend.tableView("inventory");
        String[] colNames = {"Item ID",
                             "Name",
                             "Category",
                             "Expiration Date",
                             "Refrigeration Required",
                             "Quantity",
                             "Unit"};

        // Create table and add listener
        JTable items = new JTable(data, colNames);
        items.getModel().addTableModelListener(this);

        // Create panel to hold full view
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        items.setFillsViewportHeight(true);

        // Create panel to hold edit buttons and add to full panel
        JPanel editPanel = new JPanel();
        invAddButton = new JButton("Add Item");
        invRemoveButton = new JButton("Remove Item");

        editPanel.add(invAddButton);
        editPanel.add(invRemoveButton);

        invAddButton.addActionListener(this);
        invRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(items.getSelectedRow());
                if (items.getSelectedRow() != -1) {
                    // System.out.println("Inventory, " + items.getSelectedRow());
                    // Backend.removeRecord("inventory", items.getSelectedRow());
                    frame.dispose();
                    new Manager();
                }
            }
        });

        inventoryPanel.add(editPanel, BorderLayout.BEFORE_FIRST_LINE);
        inventoryPanel.add(new JScrollPane(items), BorderLayout.CENTER);



        return inventoryPanel;
    }

    
    /** 
     * Creates GUI view for the Manager to view the store's full menu and add items to it.
     * @return JPanel containing data from SQL menu items table with editing capabilities
     */
    private JPanel menuPanel() {
        Object[][] data = Backend.tableView("menu");
        String[] colNames = {"Menu ID",
                             "Name",
                             "Price",
                             "Category",
                             "Ingredients"};
        
        // Create table and add listener
        // menTableModel = new DefaultTableModel(data, colNames);
        JTable items = new JTable(data, colNames);  
        items.getModel().addTableModelListener(this);                  
        JPanel menuPanel = new JPanel(new BorderLayout());
        items.setFillsViewportHeight(true);

        JPanel editPanel = new JPanel();
        menAddButton = new JButton("Add Item");
        menRemoveButton = new JButton("Remove Item");

        editPanel.add(menAddButton);
        editPanel.add(menRemoveButton);

        menAddButton.addActionListener(this);
        menRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(items.getSelectedRow());
                if (items.getSelectedRow() != -1) {
                    // Backend.removeRecord("menu", items.getSelectedRow());
                    frame.dispose();
                    new Manager();
                }
            }
        });

        menuPanel.add(editPanel, BorderLayout.BEFORE_FIRST_LINE);
        menuPanel.add(new JScrollPane(items), BorderLayout.CENTER);

        return menuPanel;
    }

    
    /** 
     * Creates GUI view for the Manager to view order trends, filtering data
     * by parameters such as menu item name.
     * @return JPanel containing interface to view sales trends
     */
    private JPanel trendsPanel() {
        JPanel trendsPanel = new JPanel (new BorderLayout()); 
    
        String[] colNames = {"Order ID",
                             "Order Number",
                             "Total Price Due",
                             "Date",
                             "Employee ID",
                             "Customer ID",
                             "Order Satisfied",
                             "Items Ordered"};

        JPanel editPanel = new JPanel(new BorderLayout());
        JPanel startPanel = new JPanel(new BorderLayout());
        JPanel endPanel = new JPanel(new BorderLayout());

        JLabel startLabel = new JLabel();
        startLabel.setText("Start Date");
        saleStart = new JTextField();
        startPanel.add(startLabel, BorderLayout.BEFORE_FIRST_LINE);
        startPanel.add(saleStart);
        editPanel.add(startPanel, BorderLayout.BEFORE_FIRST_LINE);

        saleStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                saleStartDate = saleStart.getText();
                if (Integer.valueOf(saleStartDate.substring(saleStartDate.length() - 2)) < Integer.valueOf(saleEndDate.substring(saleEndDate.length() - 2))) {
                    saleData = Backend.salesView(saleStartDate, saleEndDate);
                    frame.dispose();
                    new Manager();
                }
            }
        });

        JLabel endLabel = new JLabel();
        endLabel.setText("End Date");
        saleEnd = new JTextField(10);
        endPanel.add(endLabel, BorderLayout.BEFORE_FIRST_LINE);
        endPanel.add(saleEnd);
        editPanel.add(endPanel);

        saleEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                saleEndDate = saleEnd.getText();
                saleData = Backend.salesView(saleStartDate, saleEndDate);
                frame.dispose();
                new Manager();
            }
        });

        JTable sales = new JTable(saleData, colNames);
        sales.getModel().addTableModelListener(this);

        sales.setFillsViewportHeight(true);

        trendsPanel.add(editPanel, BorderLayout.BEFORE_FIRST_LINE);
        trendsPanel.add(new JScrollPane(sales), BorderLayout.CENTER);

        return trendsPanel;
    }

    /**
     * Creates GUI for Manager to view excess inventory items, i.e. items of which only 10% of the original stock were used
     * @return JPanel containing interface to view excess inventory report
     */
    private JPanel excessPanel() {
        JPanel excessReport = new JPanel();

        return excessReport;
    }

    /**
     * Cereates GUI for Manager to view inventory items that need to be restocked, i.e. items of which
     * less that 10% of the original stock remain
     * @return a JPanel for the interface to view inventory restock needs
     */
    private JPanel restockPanel() {
        JPanel restockReport = new JPanel();

        return restockReport;
    }

    
    /** 
     * This function creates the default view for the Manager GUI that
     * is active upon logging into the GUI as the Manager. Essentially an
     * empty JPanel with only the button toolbar showing.
     * @return JPanel containing the default view for the Manager GUI
     */
    private JPanel controlPanel()
    {
        JPanel p = new JPanel(new BorderLayout());

        return p;
    }

    /**
     * This function overrides the tableChange function from TableModelListener.
     * Detects data changes in a JTable and updates the corresponding SQL table.
     * @param e the event triggering the listener
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        // System.out.println("Row: " + row + " Column: " + column);
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        boolean isInv = (model.getColumnCount() == 7);
        boolean isMenu = (model.getColumnCount() == 5);
        System.out.println("Name: " + columnName + "\n" + "Row: " + row + "\n" + "Column: " + column);
        if(isInv)
        {
            if (columnName == "Refrigeration Required") {
                columnName = "fridgerequired";
            } else {
                columnName = columnName.toLowerCase();
                if (columnName.contains(" "))
                    columnName = columnName.replace(" ", "");
            }

            Backend.editTable("inventory", row, column, columnName, data);

            // inventoryPanel();
            cardPanel.add(inventoryPanel(), "inventory");
            // invTableModel.fireTableDataChanged();

            // System.out.println("In inventory");
            // Backend.getValue("inventory", "quantity", );
        }
        else if (isMenu)
        {
            columnName = columnName.toLowerCase();
            if (columnName.contains(" ")) {
                columnName = columnName.replace(" ", "");
            }
            Backend.editTable("menu", row, column, columnName, data);
            cardPanel.add(menuPanel(), "menu");
        } else {

        }
        // Need backend function to update SQL table at the specified row and column

        // Do something with the data...
    }
}