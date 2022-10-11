import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.HashMap;
import java.util.ArrayList;

public class Cashier implements ActionListener {
    
    static JFrame frame;
    static JToolBar tb;
    static JButton breakfast, entree, salads, sides, kids, treats, drinks, dipping;
    static HashMap<String, ArrayList<String[]>> menuItems = new HashMap<>(); 
    CardLayout cardLayout;
    JPanel cardPanel;

    // variables for the control panel
    static ArrayList<String> ordersList = new ArrayList<>();
    private JTextField totalField;

    public static void populateOrderList()
    {
        ordersList.add("Item#1");
        ordersList.add("Item#2");
        ordersList.add("Item#3");
        ordersList.add("Item#4");
        ordersList.add("Item#5");
    }

    public static void populateHashMap()
    {
        ArrayList<String[]> tempBreakfast = new ArrayList<>();
        tempBreakfast.add(new String[]{"Item#1", "10.00"});
        tempBreakfast.add(new String[]{"Item#2", "5.00"});
        menuItems.put("Breakfast", tempBreakfast);

        ArrayList<String[]> tempEntree = new ArrayList<>();
        tempEntree.add(new String[]{"Item#3", "7.14"});
        tempEntree.add(new String[]{"Item#4", "5.75"});
        menuItems.put("Entree", tempEntree);

        ArrayList<String[]> tempSalads = new ArrayList<>();
        tempSalads.add(new String[]{"Item#5", "6.34"});
        tempSalads.add(new String[]{"Item#6", "2.89"});
        menuItems.put("Salads", tempSalads);

        ArrayList<String[]> tempSides = new ArrayList<>();
        tempSides.add(new String[]{"Item#7", "6.00"});
        tempSides.add(new String[]{"Item#8", "2.00"});
        menuItems.put("Sides", tempSides);

        ArrayList<String[]> kidsMeals = new ArrayList<>();
        kidsMeals.add(new String[]{"Item#9", "2.58"});
        kidsMeals.add(new String[]{"Item#10", "9.99"});
        menuItems.put("Kids_Meals", kidsMeals);

        ArrayList<String[]> treats = new ArrayList<>();
        treats.add(new String[]{"Item#11", "3.71"});
        treats.add(new String[]{"Item#12", "6.89"});
        menuItems.put("Treats", treats);

        ArrayList<String[]> drinks = new ArrayList<>();
        drinks.add(new String[]{"Item#13", "3.71"});
        drinks.add(new String[]{"Item#14", "6.89"});
        menuItems.put("Drinks", drinks);

        ArrayList<String[]> sauces = new ArrayList<>();
        sauces.add(new String[]{"Item#15", "7.91"});
        sauces.add(new String[]{"Item#16", "5.39"});
        menuItems.put("Sauces", sauces);
    }

    Cashier()
    {
        populateHashMap();
        populateOrderList();

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

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == breakfast) {
            cardLayout.show(cardPanel, "breakfast");
        }
        else if (e.getSource() == entree) {
            cardLayout.show(cardPanel, "entree");
        }
        else if (e.getSource() == salads) {
            cardLayout.show(cardPanel, "salads");
        }
        else if (e.getSource() == sides) {
            cardLayout.show(cardPanel, "sides");
        }
        else if (e.getSource() == kids) {
            cardLayout.show(cardPanel, "kids");
        }
        else if (e.getSource() == treats) {
            cardLayout.show(cardPanel, "treats");
        }
        else if (e.getSource() == drinks) {
            cardLayout.show(cardPanel, "drinks");
        }
        else if (e.getSource() == dipping) {
            cardLayout.show(cardPanel, "sauces");
        }
    }

    private JPanel breakfastPanel() {

        JPanel breakfastPanel = new JPanel(new GridLayout(10, 3, 10, 10));
        for (int i = 0; i < menuItems.get("Breakfast").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);
            JLabel name = new JLabel(menuItems.get("Breakfast").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Breakfast").get(i)[1]);
            JTextField quantity = new JTextField(25);
            name.setHorizontalAlignment(JLabel.CENTER);
			innerPanel.add(name, BorderLayout.BEFORE_FIRST_LINE);
            // quantity.addActionListener(new ActionListener() {
            //     public void actionPerformed(ActionEvent e) {
            //         String input = quantity.getText();
            //         quantity.setText(input);
            //     }
            // });
            price.setHorizontalAlignment(JLabel.CENTER);
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            breakfastPanel.add(innerPanel);
        }
        return breakfastPanel;

    }

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
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            entreePanel.add(innerPanel);
        }
        return entreePanel;

    }

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
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            saladsPanel.add(innerPanel);
        }
        return saladsPanel;

    }

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
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            sidesPanel.add(innerPanel);
        }
        return sidesPanel;

    }

    private JPanel kidsPanel() {

        JPanel kidsPanel = new JPanel(new GridLayout(10, 3, 10, 10));
        for (int i = 0; i < menuItems.get("Kids_Meals").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);
            JLabel name = new JLabel(menuItems.get("Kids_Meals").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Kids_Meals").get(i)[1]);
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
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            kidsPanel.add(innerPanel);
        }
        return kidsPanel;

    }

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
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            treatsPanel.add(innerPanel);
        }
        return treatsPanel;

    }

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
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            drinksPanel.add(innerPanel);
        }
        return drinksPanel;

    }

    private JPanel saucesPanel() {

        JPanel saucesPanel = new JPanel(new GridLayout(10, 3, 10, 10));
        for (int i = 0; i < menuItems.get("Sauces").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);
            JLabel name = new JLabel(menuItems.get("Sauces").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Sauces").get(i)[1]);
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
            innerPanel.add(price);
            innerPanel.add(quantity, BorderLayout.AFTER_LAST_LINE);
            saucesPanel.add(innerPanel);
        }
        return saucesPanel;

    }

    private JPanel controlPanel()
    {
        JPanel p = new JPanel();

        p.add(orderPanel(), BorderLayout.BEFORE_FIRST_LINE);
		p.add(paymentPanel(), BorderLayout.AFTER_LAST_LINE);

        return p;
    }

    private JPanel orderPanel()
    {
        JPanel p = new JPanel();

        JPanel innerPanel = new JPanel(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font titleFont = p.getFont().deriveFont(Font.BOLD, 16f);
		
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

        String[] str = new String[ordersList.size()];
        // Assuming there is data in the list
        JList<String> orderList = new JList<>(ordersList.toArray(str));

		orderList.setVisibleRowCount(12);
		JScrollPane scrollPane = new JScrollPane(orderList);
		innerPanel.add(scrollPane, gbc);
		
		p.add(innerPanel);

        return p;
    }

    // starter code for middleware functions for updating orders list and payment box

    // private void updateOrderPanel() {
	// 	orderListModel.removeAllElements();
		
	// 	int subTotal = 0;
	// 	for (Item item : order.getItems()) {
	// 		subTotal += item.getPrice();
	// 		orderListModel.addElement(createLine(item.getName(), 
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

        p.add(innerPanel);

        return p;
    }
}
