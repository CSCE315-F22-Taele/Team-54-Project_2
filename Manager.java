/**
 * This class implements the Manager GUI using the Swing and AWT libraries.
 * At a high level, the GUI lets the manager switch between different views that
 * allow them to view and edit inventory, view and edit the store menu, and 
 * view order trends with parameter filtration capacity.
 * 
 * @author Mohona Ghosh
 * @author Estella Chen
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
    static JButton inventoryButton, menuButton, trendsButton, restockButton;
    static JButton invAddButton, invRemoveButton, menAddButton, menRemoveButton;
    static JTextField saleStart, saleEnd;

    // variables for making reports
    private String saleStartDate = "2022-10-04";
    private String saleEndDate = "2022-10-10";

    // variables for making the frame of the GUI
    CardLayout cardLayout;
    JPanel cardPanel;

    // button to go back
    private JButton backButton = new JButton("Go Back");

    Manager() {
        // creates new frame where entire layout is stored
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tb = new JToolBar();

        // create panel to hold function switches
        JPanel functionPanel = new JPanel();

        // create function switches
        inventoryButton = new JButton("Inventory");
        menuButton = new JButton("Menu Editor");
        trendsButton = new JButton("Sales/Excess Reports");
        restockButton = new JButton("View Restock Report");

        // adds all functions to the panel
        functionPanel.add(backButton);
        functionPanel.add(inventoryButton);
        functionPanel.add(menuButton);
        functionPanel.add(trendsButton);
        functionPanel.add(restockButton);

        // adds actionlisteners to all functions
        inventoryButton.addActionListener(this);
        menuButton.addActionListener(this);
        trendsButton.addActionListener(this);
        backButton.addActionListener(this);
        restockButton.addActionListener(this);

        // Setting up panel with the frame and displaying frame
        frame.add(functionPanel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);

        // make cards for switching logic and add actual material to each view
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // calls the respective functions and adds them to the panel
        cardPanel.add(inventoryPanel(), "inventory");
        cardPanel.add(menuPanel(), "menu editor");
        cardPanel.add(trendsPanel(), "trends");
        cardPanel.add(restockPanel(), "restock");

        // adds the functions to the final frame
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
        // action listeners for the different card panels
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
            // adds items to inventory
            Backend.addEmptyCell("inventory");
            frame.dispose();
            new Manager();
        } else if (e.getSource() == menAddButton) {
            // adds items to the menu
            Backend.addEmptyCell("menu");
            frame.dispose();
            new Manager();
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

        // adds functionality to removing items in the inventory
        invRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(items.getSelectedRow());
                if (items.getSelectedRow() != -1) {
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
        
        // creates a table that displays the items in the database
        JTable items = new JTable(data, colNames);  
        items.getModel().addTableModelListener(this);                  
        JPanel menuPanel = new JPanel(new BorderLayout());
        items.setFillsViewportHeight(true);

        // creates the panel that shows the buttons to edit the table
        JPanel editPanel = new JPanel();
        menAddButton = new JButton("Add Item");
        menRemoveButton = new JButton("Remove Item");

        // adds buttons to the panel
        editPanel.add(menAddButton);
        editPanel.add(menRemoveButton);

        menAddButton.addActionListener(this);

        // action listener that will remove the items from the table
        menRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(items.getSelectedRow());
                if (items.getSelectedRow() != -1) {
                    frame.dispose();
                    new Manager();
                }
            }
        });

        // adds the buttons and table to the panel
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
        // creates a bunch of different panels that will store labels and buttons used later
        JPanel trendsPanel = new JPanel (new BorderLayout()); 
        JPanel editPanel = new JPanel(new BorderLayout());
        JPanel startPanel = new JPanel(new BorderLayout());
        JPanel endPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // creates sales and excess button that will generate those respeective reports
        JButton salesButton = new JButton("View sales report");
        JButton excessButton = new JButton("View excess report");
        buttonPanel.add(salesButton, BorderLayout.BEFORE_FIRST_LINE);
        buttonPanel.add(excessButton, BorderLayout.PAGE_END);
        salesButton.addActionListener(this);
        excessButton.addActionListener(this);

        // adds labels and text fields that store the start and end dates
        JLabel startLabel = new JLabel();
        startLabel.setText("Start Date");
        saleStart = new JTextField();
        startPanel.add(startLabel, BorderLayout.BEFORE_FIRST_LINE);
        startPanel.add(saleStart);
        editPanel.add(startPanel, BorderLayout.BEFORE_FIRST_LINE);

        // adds actionlisteners that will create a new frame that show the sales report
        saleStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                saleStartDate = saleStart.getText();
            }
        });

        // creates new label for the end date
        JLabel endLabel = new JLabel();
        endLabel.setText("End Date");
        saleEnd = new JTextField(10);
        endPanel.add(endLabel, BorderLayout.BEFORE_FIRST_LINE);
        endPanel.add(saleEnd);
        editPanel.add(endPanel, BorderLayout.AFTER_LAST_LINE);

        saleEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                saleEndDate = saleEnd.getText();
            }
        });

        // generates the sales report with a new window
        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                new SalesReport(saleStartDate, saleEndDate);
            }
        });

        // generates the excess report with a new window
        excessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                new ExcessReport(saleStartDate, saleEndDate);
            }
        });

        trendsPanel.add(editPanel, BorderLayout.BEFORE_FIRST_LINE);
        trendsPanel.add(buttonPanel, BorderLayout.SOUTH);

        return trendsPanel;
    }

    /**
     * Cereates GUI for Manager to view inventory items that need to be restocked, i.e. items of which
     * less that 10% of the original stock remain
     * @return a JPanel for the interface to view inventory restock needs
     */
    private JPanel restockPanel() {
        JPanel restockReport = new JPanel();
        JButton restockButton = new JButton("restockButton");
        restockButton.addActionListener(this);

        // generates the restock report with a new frame
        restockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                new RestockReport();
            }
        });

        restockReport.add(restockButton);
        
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

            cardPanel.add(inventoryPanel(), "inventory");
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
    }
}