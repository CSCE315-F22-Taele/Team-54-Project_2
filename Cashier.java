/**
 * This class creates the Cashier GUI using Swing and AWT libraries.
 * Allows the cashier to view menu items with their prices and add
 * items to a customer order and get the total order price.
 * @author Estella Chen
 * @author Neha Sujith
 */
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class Cashier extends Backend implements ActionListener {
    
    static JFrame frame;
    static JToolBar tb;
    // defining categories of Chick-fil-A menu items here
    static JButton breakfast, entree, salads, sides, kids, treats, drinks, dipping;
    static JButton removeButton;
    private JButton backButton = new JButton("Go Back");
    static HashMap<String, ArrayList<String[]>> menuItems = new HashMap<>();

    CardLayout cardLayout;
    JPanel cardPanel;

    // variables for the control panel
    private DefaultListModel<String> orderListModel;
    private JList<String> orderList;
    private JTextField totalField;
    private double cost;

    /**
     * Populates the menuItems HashMap of menu items sorted by category. Used to populate the Cashier GUI view by panel.
     * Called in the Cashier constructor, meaning the Cashier GUI will always mirror an updated inventory.
     */
    public static void populateHashMap(){
        String[] categoryNames = {"Breakfast", "Entree", "Salads", "Sides", "Kids Meals", "Treats", "Drinks", "Sauce"};
        for (String name: categoryNames) {
            ArrayList<String[]> tempItems = new ArrayList<>();
            ArrayList<HashMap<String, String>> catItems = Backend.getNValues("menu", "category", name, 100);
            for (HashMap<String, String> item: catItems) {
                tempItems.add(new String[]{item.get("name"), item.get("price")});
            }

            menuItems.put(name, tempItems);
        }
    }

    /**
     * Constructs an initial view of the Cashier GUI. Sets frame dimensions and adds panels for each menu
     * category, current order, and payment. Also adds buttons for each menu category panel and checkout.
     * Adds event listeners and populates menuItems hashmap. Default view of Cashier GUI is set to menu items
     * in the Breakfast category; order and payment panels are persistent.
     */
    Cashier(){
        populateHashMap();
        this.orderListModel = new DefaultListModel<>();

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tb = new JToolBar(); // toolbar to hold category buttons
 
        // Create a menu panel
        JPanel menuPanel = new JPanel();

        // make buttons for each menu category to display items under that category listing
        breakfast = new JButton("Breakfast");
        entree = new JButton("Entree");
        salads = new JButton("Salads");
        sides = new JButton("Sides");
        kids = new JButton("Kids Meals");
        treats = new JButton("Treats");
        drinks = new JButton("Drinks");
        dipping = new JButton("Sauces");

        // add back button
        menuPanel.add(backButton);
        backButton.addActionListener(this);

        // add buttons
        menuPanel.add(breakfast);
        menuPanel.add(entree);
        menuPanel.add(salads);
        menuPanel.add(sides);
        menuPanel.add(kids);
        menuPanel.add(treats);
        menuPanel.add(drinks);
        menuPanel.add(dipping);

        // Add event listeners for each menu category button
        breakfast.addActionListener(this);
        entree.addActionListener(this);
        salads.addActionListener(this);
        sides.addActionListener(this);
        kids.addActionListener(this);
        treats.addActionListener(this);
        drinks.addActionListener(this);
        dipping.addActionListener(this);
        
        frame.add(menuPanel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);

        // All panels are added to cardLayout, which will display specific panels based on which menu category
        // is selected.
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(breakfastPanel(), "breakfast");
        cardPanel.add(entreePanel(), "entree");
        cardPanel.add(saladsPanel(), "salads");
        cardPanel.add(sidesPanel(), "sides");
        cardPanel.add(kidsPanel(), "kids");
        cardPanel.add(treatsPanel(), "treats");
        cardPanel.add(drinksPanel(), "drinks");
        cardPanel.add(saucesPanel(), "sauces");
        
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(controlPanel(), BorderLayout.AFTER_LINE_ENDS);

        // set basic Cashier frame dimensions and characteristics
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }
  
    /** 
     *  This method switches panel based on selected menu category.
     *  @param ActionEvent of buttons
     */
    @Override
    public void actionPerformed(ActionEvent e){
        // Switch the displayed menu items based on button pressed
        if (e.getSource() == breakfast) {
            cardLayout.show(cardPanel, "breakfast");
        } else if (e.getSource() == entree) {
            cardLayout.show(cardPanel, "entree");
        } else if (e.getSource() == salads) {
            cardLayout.show(cardPanel, "salads");
        } else if (e.getSource() == sides) {
            cardLayout.show(cardPanel, "sides");
        } else if (e.getSource() == kids) {
            cardLayout.show(cardPanel, "kids");
        } else if (e.getSource() == treats) {
            cardLayout.show(cardPanel, "treats");
        } else if (e.getSource() == drinks) {
            cardLayout.show(cardPanel, "drinks");
        } else if (e.getSource() == dipping) {
            cardLayout.show(cardPanel, "sauces");
        } else if (e.getSource() == backButton) {
            frame.dispose();
            new LaunchPage();
        }
    }

    
    /** 
     * Returns panel that shows breakfast menu items.
     * @return JPanel breakfast panel
     */
    private JPanel breakfastPanel() {

        JPanel breakfastPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        // Adds breakfast menu items to breakfast panel
        for (int i = 0; i < menuItems.get("Breakfast").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Breakfast").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Breakfast").get(i)[1]);
            JTextField quantity = new JTextField(25);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            // Allows a quantity to be entered which will add to the customer's order.
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);

                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Breakfast").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Breakfast").get(menuNum)[1]);
                    }

                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());

			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Breakfast").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Breakfast").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            breakfastPanel.add(innerPanel);
        }
        return breakfastPanel;
    }

    
    /** 
     * Returns panel that shows entree menu items.
     * @return JPanel entree panel
     */
    private JPanel entreePanel() {

        JPanel entreePanel = new JPanel(new GridLayout(10, 3, 10, 10));

        for (int i = 0; i < menuItems.get("Entree").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Entree").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Entree").get(i)[1]);
            JTextField quantity = new JTextField(10);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);
                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Entree").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Entree").get(menuNum)[1]);
                    }

                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Entree").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Entree").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.valueOf(cost));
                }
            });
            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            entreePanel.add(innerPanel);
        }
        return entreePanel;
    }

    
    /** 
     * Returns panel that shows salads menu items.
     * @return JPanel salads panel
     */
    private JPanel saladsPanel() {

        JPanel saladsPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        for (int i = 0; i < menuItems.get("Salads").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Salads").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Salads").get(i)[1]);
            JTextField quantity = new JTextField(10);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);

                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Salads").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Salads").get(menuNum)[1]);
                    }

                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Salads").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Salads").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.format("%#.2f", cost));
                }
            });
            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            saladsPanel.add(innerPanel);
        }
        return saladsPanel;
    }

    
    /** 
     * Returns panel that shows sides menu items.
     * @return JPanel sides panel
     */
    private JPanel sidesPanel() {

        JPanel sidesPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        for (int i = 0; i < menuItems.get("Sides").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Sides").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Sides").get(i)[1]);
            JTextField quantity = new JTextField(10);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);

                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Sides").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Sides").get(menuNum)[1]);
                    }

                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Sides").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Sides").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.format("%#.2f", cost));
                }
            });
            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            sidesPanel.add(innerPanel);
        }
        return sidesPanel;
    }

    
    /** 
     * Returns panel that shows Kids Menu items.
     * @return JPanel kids menu panel
     */
    private JPanel kidsPanel() {

        JPanel kidsPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        for (int i = 0; i < menuItems.get("Kids Meals").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Kids Meals").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Kids Meals").get(i)[1]);
            JTextField quantity = new JTextField(10);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);

                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Kids Meals").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Kids Meals").get(menuNum)[1]);
                    }

                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Kids Meals").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Kids Meals").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.format("%#.2f", cost));
                }
            });
            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            kidsPanel.add(innerPanel);
        }
        return kidsPanel;
    }

    
    /** 
     * Returns panel that shows treats menu items.
     * @return JPanel treats panel
     */
    private JPanel treatsPanel() {

        JPanel treatsPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        for (int i = 0; i < menuItems.get("Treats").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Treats").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Treats").get(i)[1]);
            JTextField quantity = new JTextField(10);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);

                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Treats").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Treats").get(menuNum)[1]);
                    }
                    
                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Treats").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Treats").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.format("%#.2f", cost));
                }
            });
            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            treatsPanel.add(innerPanel);
        }
        return treatsPanel;
    }

    
    /** 
     * Returns panel that shows drinks menu items.
     * @return JPanel drinks panel
     */
    private JPanel drinksPanel() {

        JPanel drinksPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        // display each menu item under the Drinks category
        for (int i = 0; i < menuItems.get("Drinks").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Drinks").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Drinks").get(i)[1]);
            JTextField quantity = new JTextField(10);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);

                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Drinks").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Drinks").get(menuNum)[1]);
                    }

                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Drinks").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Drinks").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            drinksPanel.add(innerPanel);
        }
        return drinksPanel;
    }

    
    /** 
     * Returns panel that shows sauces menu items.
     * @return JPanel sauces panel
     */
    private JPanel saucesPanel() {

        JPanel saucesPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        for (int i = 0; i < menuItems.get("Sauce").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);

            JLabel name = new JLabel(menuItems.get("Sauce").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Sauce").get(i)[1]);
            JTextField quantity = new JTextField(10);

            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);

            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);

                    for (int j = 0; j < num; j++) {
                        orderListModel.addElement(menuItems.get("Sauce").get(menuNum)[0]);
                        cost += Double.valueOf(menuItems.get("Sauce").get(menuNum)[1]);
                    }

                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            price.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);

            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);

            // Removes every instance of selected order item from customer's order
            // If customer wants to change quantity of an item, this remove button must be selected,
            // and cashier must enter the correct quantity again to add to customer's order
            removeButton = new JButton("Remove Item");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double tempCost = 0.0;

                    while (orderListModel.removeElement(menuItems.get("Sauce").get(menuNum)[0])) {
                        tempCost += Double.valueOf(menuItems.get("Sauce").get(menuNum)[1]);
                    }

                    cost -= tempCost;
                    totalField.setText(String.format("%#.2f", cost));
                }
            });

            buttonPanel.add(removeButton);
            innerPanel.add(buttonPanel, BorderLayout.AFTER_LINE_ENDS);

            saucesPanel.add(innerPanel);
        }
        return saucesPanel;
    }

    
    /** 
     * Creates the control panel, which is the panel on the side that shows the order and payment panels.
     * @return JPanel, specifically the control panel that contains order and payment
     */
    private JPanel controlPanel(){
        JPanel p = new JPanel(new BorderLayout());

        p.add(orderPanel(), BorderLayout.BEFORE_FIRST_LINE);
		p.add(paymentPanel(), BorderLayout.AFTER_LAST_LINE);

        return p;
    }


    /** 
     * Creates and returns the order panel, which shows what the user has selected to order in the GUI.
     * @return JPanel, specifically the order panel
     */
    private JPanel orderPanel(){
        JPanel p = new JPanel();

        // innerPanel is the panel that keeps track of the current order
        JPanel innerPanel = new JPanel(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font titleFont = p.getFont().deriveFont(Font.BOLD, 16f);

        orderList = new JList<>(orderListModel);
		
        // this chunk of code places the order panel to the right of the screen
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JLabel label = new JLabel("Order");
		label.setFont(titleFont);
		innerPanel.add(label, gbc);
		
		gbc.gridy++;

		orderList.setVisibleRowCount(12);
		JScrollPane scrollPane = new JScrollPane(orderList);
		innerPanel.add(scrollPane, gbc);
		
		p.add(innerPanel);

        return p;
    }


    /** 
     * Creates and returns the payment panel, which shows the total payment of all the ordered items the user made.
     * @return JPanel, specifically the payment panel
     */
    private JPanel paymentPanel(){
        JPanel p = new JPanel();

        JPanel innerPanel = new JPanel(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font titleFont = p.getFont().deriveFont(Font.BOLD, 16f);
		
        // this chunk of code places the payment panel to the right of the screen
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 0d;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
        // Create labels for total payment
		JLabel label = new JLabel("Payment");
		label.setFont(titleFont);
		innerPanel.add(label, gbc);

        gbc.gridwidth = 1;
		gbc.gridy++;
		JLabel totalLabel = new JLabel("Total:");
		innerPanel.add(totalLabel, gbc);
		
        // set dimensions of the payment panel
		gbc.weightx = 1d;
		gbc.gridx++;
		totalField = new JTextField(10);
		totalField.setEditable(false);
		totalField.setHorizontalAlignment(JTextField.TRAILING);
		innerPanel.add(totalField, gbc);

        gbc.gridwidth = 2;
		gbc.weightx = 1d;
		gbc.gridx = 0;
		gbc.gridy++;

        // this order button has three functions:
        // - add customer's order to order SQL table
        // - clear order panel for next customer order
        // - deplete inventory table when order is made
        JButton button = new JButton("Take Order");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HashMap<String,String> order = new HashMap<>();
                Random rnd = new Random();

                order.put("orderid", String.valueOf(rnd.nextInt(1000)));
                order.put("ordernumber", String.valueOf(rnd.nextInt(100000)));

                String items = "{";
                for (int i = 0; i < orderListModel.getSize(); i++) {
                    items += orderListModel.get(i);
                    if (i < orderListModel.getSize()-1 ) {
                        items += ", ";
                    }
                }
                items += "}";

                order.put("totalprice", String.valueOf(cost));
                order.put("saledate", "2022-11-30");

                order.put("employeeid", String.valueOf(rnd.nextInt(87000)));
                order.put("customerid", String.valueOf(rnd.nextInt(600)));
                
                order.put("satisfied", "t");
                order.put("itemsordered", items);
                System.out.println(items);

                Backend.updateInventoryFromOrder(items);

                totalField.setText("");

                Boolean didWeInsert  = Backend.addValue("orders", order);
                System.out.println(didWeInsert);

                orderListModel.clear();
                cost = 0.0;
            }
        });

		innerPanel.add(button, gbc);

        p.add(innerPanel);

        return p;
    }
}
