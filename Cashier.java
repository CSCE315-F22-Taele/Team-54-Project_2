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
    JPanel card_panel;

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

        ArrayList<String[]> kidsMeals = new ArrayList<>();
        kidsMeals.add(new String[]{"Item#7", "2.58"});
        kidsMeals.add(new String[]{"Item#8", "9.99"});
        menuItems.put("Kids_Meals", kidsMeals);

        ArrayList<String[]> treats = new ArrayList<>();
        treats.add(new String[]{"Item#9", "3.71"});
        treats.add(new String[]{"Item#10", "6.89"});
        menuItems.put("Treats", treats);

        ArrayList<String[]> drinks = new ArrayList<>();
        drinks.add(new String[]{"Item#11", "3.71"});
        drinks.add(new String[]{"Item#12", "6.89"});
        menuItems.put("Drinks", drinks);

        ArrayList<String[]> sauces = new ArrayList<>();
        sauces.add(new String[]{"Item#13", "7.91"});
        sauces.add(new String[]{"Item#14", "5.39"});
        menuItems.put("Sauces", sauces);
    }

    Cashier()  {
        populateHashMap();
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tb = new JToolBar();
 
        // create a panel
        JPanel menu_panel = new JPanel();

        breakfast = new JButton("Breakfast");
        entree = new JButton("Entree");
        salads = new JButton("Salads");
        sides = new JButton("Sides");
        kids = new JButton("Kids Meals");
        treats = new JButton("Treats");
        drinks = new JButton("Drinks");
        dipping = new JButton("Sauces");

        // add buttons
        menu_panel.add(breakfast);
        menu_panel.add(entree);
        menu_panel.add(salads);
        menu_panel.add(sides);
        menu_panel.add(kids);
        menu_panel.add(treats);
        menu_panel.add(drinks);
        menu_panel.add(dipping);

        breakfast.addActionListener(this);
        entree.addActionListener(this);
        salads.addActionListener(this);
        
        frame.add(menu_panel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);
        cardLayout = new CardLayout();
        card_panel = new JPanel(cardLayout);
        card_panel.add(breakfast_panel(), "breakfast");
        card_panel.add(entree_panel(), "entree");
        card_panel.add(salads_panel(), "salads");
        
        frame.add(card_panel, BorderLayout.CENTER);
        frame.add(orderPanel(), BorderLayout.AFTER_LINE_ENDS);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationByPlatform(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == breakfast) {
            cardLayout.show(card_panel, "breakfast");
        }
        if (e.getSource() == entree) {
            cardLayout.show(card_panel, "entree");
        }
        if (e.getSource() == salads) {
            cardLayout.show(card_panel, "salads");
        }
    }

    private JPanel breakfast_panel() {

        JPanel breakfast_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            breakfast_panel.add(innerPanel);
        }
        return breakfast_panel;

    }

    private JPanel entree_panel() {

        JPanel entree_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            entree_panel.add(innerPanel);
        }
        return entree_panel;

    }

    private JPanel salads_panel() {

        JPanel salads_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            salads_panel.add(innerPanel);
        }
        return salads_panel;

    }

    private JPanel orderPanel() {
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
		
		JLabel label = new JLabel("Order");
		label.setFont(titleFont);
		innerPanel.add(label, gbc);

        gbc.gridwidth = 1;
		gbc.gridy++;
		JLabel totalLabel = new JLabel("Total:");
		innerPanel.add(totalLabel, gbc);
		
		gbc.weightx = 1d;
		gbc.gridx++;
		JTextField totalField = new JTextField(10);
		totalField.setEditable(false);
		totalField.setHorizontalAlignment(JTextField.TRAILING);
		innerPanel.add(totalField, gbc);

        p.add(innerPanel);

        return p;
    }
}
