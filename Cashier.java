import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Cashier {
    
    static JFrame frame;
    static JToolBar tb;
    static JButton breakfast, entree, salads, sides, kids, treats, drinks, dipping;
    static HashMap<String, ArrayList<String[]>> menuItems = new HashMap<>(); 

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
        
        frame.add(menu_panel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.setSize(500, 500);
        frame.setVisible(true);

        breakfast.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(breakfast_panel(), BorderLayout.CENTER);
            }
        });

    }

    private JPanel breakfast_panel() {

        JPanel breakfast_panel = new JPanel(new GridLayout(2, 2, 10, 10));
        for (int i = 0; i < menuItems.get("Breakfast").size(); i++) {
            JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(Color.WHITE);
            // [Breakfast: [[apple, 2], [orange, 3]]]
            JLabel name = new JLabel(menuItems.get("Breakfast").get(i)[0]);
            JLabel price = new JLabel(menuItems.get("Breakfast").get(i)[1]);
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
            breakfast_panel.add(innerPanel);
        }
        return breakfast_panel;

    }
}
