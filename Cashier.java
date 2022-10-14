/**
 * This class creates the Cashier GUI using Swing and AWT libraries.
 * Allows the cashier to view menu items with their prices and add
 * items to a customer order and get the total order price.
 * @author Estella Chen
 * @author Neha Sujith
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.*;

import java.util.HashMap;
import java.util.ArrayList;

public class Cashier extends Backend implements ActionListener {
    
    static JFrame frame;
    static JToolBar tb;
    static JButton breakfast, entree, salads, sides, kids, treats, drinks, dipping;
    static HashMap<String, ArrayList<String[]>> menuItems = new HashMap<>();
    CardLayout cardLayout;
    JPanel cardPanel;

    static JButton removeButton;
    private JButton backButton = new JButton("Go Back");

    // variables for the control panel
    public ArrayList<String> ordersList = new ArrayList<>();
    private JList<String> orderList;
    private JTextField totalField;

    public static void populateHashMap()
    {
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

    Cashier()
    {
        populateHashMap();
        // populateOrderList();

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tb = new JToolBar();
 
        // create a panel
        JPanel menuPanel = new JPanel();

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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        System.out.println(ordersList.size());

    }
  
    /** 
     *  This method allows for the logic of panel switching upon
     *  the clicking of a specific menu category.
     *  @param ActionEvent of buttons
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
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
        } else if (e.getSource() == removeButton) {
            // functionality for this button will be implemented in phase 4
        } // else if (e.getSource() == )
    }

    
    /** 
     * Returns panel that shows breakfast menu items.
     * @return JPanel breakfast panel
     */
    private JPanel breakfastPanel() {

        JPanel breakfastPanel = new JPanel(new GridLayout(10, 3, 10, 10));

        // Adds menu items to the panel
        for (int i = 0; i < menuItems.get("Breakfast").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);
            JLabel name = new JLabel(menuItems.get("Breakfast").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Breakfast").get(i)[1]);
            JTextField quantity = new JTextField(25);
            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);
            int menuNum = i;
            quantity.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = quantity.getText();
                    int num = Integer.valueOf(input);
                    for (int j = 0; j < num; j++) {
                        ordersList.add(menuItems.get("Breakfast").get(menuNum)[0]);
                        //insert into sql table
                        HashMap<String, String> newItem = new HashMap<String, String>();
                        newItem.put("name", menuItems.get("Breakfast").get(menuNum)[0]);
                        newItem.put("price", menuItems.get("Breakfast").get(menuNum)[1]);
                        Backend.addValue("temp", newItem);
                        // JPanel p = orderPanel();
                        // frame.add(controlPanel()/*, BorderLayout.AFTER_LINE_ENDS*/);
                    }

                    frame.dispose();
                    new Cashier();

                    System.out.println(ordersList);
                    
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
            removeButton = new JButton("Remove Item");
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
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);
            removeButton = new JButton("Remove Item");
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
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);
            removeButton = new JButton("Remove Item");
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
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);
            removeButton = new JButton("Remove Item");
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
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);
            removeButton = new JButton("Remove Item");
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
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);
            removeButton = new JButton("Remove Item");
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
        for (int i = 0; i < menuItems.get("Drinks").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);
            JLabel name = new JLabel(menuItems.get("Drinks").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Drinks").get(i)[1]);
            JTextField quantity = new JTextField(10);
            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);
            removeButton = new JButton("Remove Item");
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
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(Color.WHITE);
            textPanel.add(price);
            textPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            innerPanel.add(textPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.setBackground(Color.WHITE);
            removeButton = new JButton("Remove Item");
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
    private JPanel controlPanel()
    {
        JPanel p = new JPanel(new BorderLayout());

        p.add(orderPanel(), BorderLayout.BEFORE_FIRST_LINE);
		p.add(paymentPanel(), BorderLayout.AFTER_LAST_LINE);

        return p;
    }

    
    /** 
     * Creates and returns the order panel, which shows what the user has selected to order in the GUI.
     * @return JPanel, specifically the order panel
     */
    private JPanel orderPanel()
    {
        JPanel p = new JPanel();

        // innerPanel is the panel that keeps track of the current order
        JPanel innerPanel = new JPanel(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font titleFont = p.getFont().deriveFont(Font.BOLD, 16f);
        // Add a JTable to the Order window
        String[][] data = Backend.tableView("temp");
        String[] colNames = {"Name",
                             "Price"};

        // Create table and add listener
        JTable currOrder = new JTable(data, colNames);
        DefaultTableModel model = new DefaultTableModel(data, colNames);
        for (int i = 0; i < ordersList.size(); i++) {
            model.addRow(new String[]{ordersList.get(i), "0"});
        }
		
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
        innerPanel.add(new JScrollPane(currOrder),gbc);
		
		gbc.gridy++;

        String[] str = new String[ordersList.size()];
        // Assuming there is data in the list
        orderList = new JList<>(ordersList.toArray(str));

		orderList.setVisibleRowCount(12);
		JScrollPane scrollPane = new JScrollPane(orderList);
		innerPanel.add(scrollPane, gbc);
		
		p.add(innerPanel);

        return p;
    }

    
    // starter code for middleware functions for updating orders list and payment box

    // private void updateOrderPanel() {
	// 	orderList.removeAllElements();
		
	// 	int subTotal = 0;
	// 	for (Item item : order.getItems()) {
	// 		subTotal += item.getPrice();
	// 		orderList.addElement(createLine(item.getName(), 
	// 				item.getPrice()));
	// 	}
		
	// 	double total = subTotal * 0.01d;
	// 	double tax = total * 0.09d;
	// 	total += tax;
	// 	order.setOrderTotal(total);
	// 	totalField.setText(String.format("%#.2f", total)); 
		
		
	// 	paymentListener.makeChange();
	// }

    // public class OrderListener implements ActionListener {

	// 	@Override
	// 	public void actionPerformed(ActionEvent event) {
	// 		JButton button = (JButton) event.getSource();
	// 		if (button.getText().equals("Remove Item")) {
	// 			removeItem(Integer.valueOf(event.getActionCommand()));
	// 		} else {
	// 			addItem(Integer.valueOf(event.getActionCommand()));
	// 		}
	// 		updateOrderPanel();
	// 	}
		
	// 	private void removeItem(int index) {
	// 		Item item = inventory.getItem(index);
	// 		order.removeItem(item);
	// 	}
		
	// 	private void addItem(int index) {
	// 		Item item = inventory.getItem(index);
	// 		order.addItem(item);
	// 	}
		
	// }

    /** 
     * Creates and returns the payment panel, which shows the total payment of all the ordered items the user made.
     * @return JPanel, specifically the payment panel
     */
    private JPanel paymentPanel()
    {
        JPanel p = new JPanel();

        JPanel innerPanel = new JPanel(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font titleFont = p.getFont().deriveFont(Font.BOLD, 16f);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 0d;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JLabel label = new JLabel("Payment");
		label.setFont(titleFont);
		innerPanel.add(label, gbc);

        gbc.gridwidth = 1;
		gbc.gridy++;
		JLabel totalLabel = new JLabel("Total:");
		innerPanel.add(totalLabel, gbc);
		
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
        JButton button = new JButton("Take Order");
		// button.addActionListener(paymentListener);
		innerPanel.add(button, gbc);

        p.add(innerPanel);

        return p;
    }


}
