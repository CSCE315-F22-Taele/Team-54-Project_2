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
        sides.addActionListener(this);
        kids.addActionListener(this);
        treats.addActionListener(this);
        drinks.addActionListener(this);
        dipping.addActionListener(this);
        
        frame.add(menu_panel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);
        cardLayout = new CardLayout();
        card_panel = new JPanel(cardLayout);
        card_panel.add(breakfast_panel(), "breakfast");
        card_panel.add(entree_panel(), "entree");
        card_panel.add(salads_panel(), "salads");
        card_panel.add(sides_panel(), "sides");
        card_panel.add(kids_panel(), "kids");
        card_panel.add(treats_panel(), "treats");
        card_panel.add(drinks_panel(), "drinks");
        card_panel.add(sauces_panel(), "sauces");
        
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
        else if (e.getSource() == entree) {
            cardLayout.show(card_panel, "entree");
        }
        else if (e.getSource() == salads) {
            cardLayout.show(card_panel, "salads");
        }
        else if (e.getSource() == sides) {
            cardLayout.show(card_panel, "sides");
        }
        else if (e.getSource() == kids) {
            cardLayout.show(card_panel, "kids");
        }
        else if (e.getSource() == treats) {
            cardLayout.show(card_panel, "treats");
        }
        else if (e.getSource() == drinks) {
            cardLayout.show(card_panel, "drinks");
        }
        else if (e.getSource() == dipping) {
            cardLayout.show(card_panel, "sauces");
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

    private JPanel sides_panel() {

        JPanel sides_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            sides_panel.add(innerPanel);
        }
        return sides_panel;

    }

    private JPanel kids_panel() {

        JPanel kids_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            kids_panel.add(innerPanel);
        }
        return kids_panel;

    }

    private JPanel treats_panel() {

        JPanel treats_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            treats_panel.add(innerPanel);
        }
        return treats_panel;

    }

    private JPanel drinks_panel() {

        JPanel drinks_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            drinks_panel.add(innerPanel);
        }
        return drinks_panel;

    }

    private JPanel sauces_panel() {

        JPanel sauces_panel = new JPanel(new GridLayout(10, 3, 10, 10));
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
            sauces_panel.add(innerPanel);
        }
        return sauces_panel;

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
