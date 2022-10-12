import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Manager implements ActionListener {
    
    // Boilerplate Code
    private JFrame frame;
    private JToolBar tb;
    static JButton inventoryButton, menuButton, trendsButton;

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
        }
    }

    
    /** 
     * Creates GUI view for the Manager to view inventory data and edit inventory records.
     * @return JPanel containing the inventory data with editing capabilities
     */
    private JPanel inventoryPanel() {
        Object[][] data = { // url: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
                            // url: https://stackoverflow.com/questions/27815400/retrieving-data-from-jdbc-database-into-jtable
                            }; // import from SQL table????? Function in middleware table?????
        String[] colNames = {"Item ID",
                             "Name",
                             "Category",
                             "Expiration Date",
                             "Refrigeration Required",
                             "Quantity",
                             "Unit"};

        JTable items = new JTable(data, colNames);
        JPanel inventoryPanel = new JPanel();
        items.setFillsViewportHeight(true);

        inventoryPanel.add(new JScrollPane(items));

        return inventoryPanel;
    }

    
    /** 
     * Creates GUI view for the Manager to view the store's full menu and add items to it.
     * @return JPanel containing data from SQL menu items table with editing capabilities
     */
    private JPanel menuPanel() {
        Object[][] data = { // see inventoryPanel(); also we need a menu table in SQL ASAP
                            };
        String[] colNames = {"Item ID",
                             "Name",
                             "Category",
                             "Price"};
        
        JTable items = new JTable(data, colNames);                    
        JPanel menuPanel = new JPanel();
        items.setFillsViewportHeight(true);

        menuPanel.add(new JScrollPane(items));

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

        // p.add(orderPanel(), BorderLayout.BEFORE_FIRST_LINE);
		// p.add(paymentPanel(), BorderLayout.AFTER_LAST_LINE);

        return p;
    }
}