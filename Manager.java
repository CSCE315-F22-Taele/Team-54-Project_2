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
import javax.swing.table.TableModel;

public class Manager implements ActionListener, TableModelListener {
    
    // Boilerplate Code
    private JFrame frame;
    private JToolBar tb;
    static JButton inventoryButton, menuButton, trendsButton;
    static JButton invAddButton, invRemoveButton, menAddButton, menRemoveButton;

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
        trendsButton = new JButton("View Order Trends"); // NOTE: this button does not work yet (coming in Phase 4)

        functionPanel.add(backButton);
        functionPanel.add(inventoryButton);
        functionPanel.add(menuButton);
        functionPanel.add(trendsButton);

        inventoryButton.addActionListener(this);
        menuButton.addActionListener(this);
        trendsButton.addActionListener(this);
        backButton.addActionListener(this);

        // Setting up panel with the frame and displaying frame
        frame.add(functionPanel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);

        // make cards for switching logic and add actual material to each view
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(inventoryPanel(), "inventory");
        cardPanel.add(menuPanel(), "menu editor");
        cardPanel.add(trendsPanel(), "trends");

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

        } else if (e.getSource() == invRemoveButton) {

        } else if (e.getSource() == menAddButton) {

        } else if (e.getSource() == menRemoveButton) {

        }
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
        invRemoveButton.addActionListener(this);

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
        String[] colNames = {"Item ID",
                             "Name",
                             "Price",
                             "Category",
                             "Ingredients"};
        
        // Create table and add listener
        JTable items = new JTable(data, colNames);                    
        JPanel menuPanel = new JPanel(new BorderLayout());
        items.setFillsViewportHeight(true);

        JPanel editPanel = new JPanel();
        menAddButton = new JButton("Add Item");
        menRemoveButton = new JButton("Remove Item");

        editPanel.add(menAddButton);
        editPanel.add(menRemoveButton);

        menAddButton.addActionListener(this);
        menRemoveButton.addActionListener(this);

        menuPanel.add(editPanel, BorderLayout.BEFORE_FIRST_LINE);
        menuPanel.add(new JScrollPane(items), BorderLayout.CENTER);

        return menuPanel;
    }

    
    /** 
     * Creates GUI view for the Manager to view order trends, filtering data
     * by parameters such as menu item name.
     * @return JPanel containing interface to view order trends
     */
    // NOTE: will not be implemented until Phase 4
    private JPanel trendsPanel() {
        JPanel trendsPanel = new JPanel ();

        return trendsPanel;
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

        System.out.println("Row: " + row + " Column: " + column);
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);

        // Need backend function to update SQL table at the specified row and column

        // Do something with the data...
    }
}