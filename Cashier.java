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
        tempBreakfast.add(new String[]{"Item#3", "7.14"});
        tempBreakfast.add(new String[]{"Item#4", "5.75"});
        menuItems.put("Entree", tempEntree);

        ArrayList<String[]> tempSalads = new ArrayList<>();
        tempSalads.add(new String[]{"Item#5", "6.34"});
        tempSalads.add(new String[]{"Item#6", "2.89"});
        menuItems.put("Salads", tempSalads);

        ArrayList<String[]> kidsMeals = new ArrayList<>();
        kidsMeals.add(new String[]{"Item#7", "2.58"});
        kidsMeals.add(new String[]{"Item#8", "9.99"});
        menuItems.put("Kids Meals", kidsMeals);

        ArrayList<String[]> treats = new ArrayList<>();
        treats.add(new String[]{"Item#9", "3.71"});
        treats.add(new String[]{"Item#10", "6.89"});
        menuItems.put("Treats", treats);

        ArrayList<String[]> drinks = new ArrayList<>();
        drinks.add(new String[]{"Item#11", "3.71"});
        drinks.add(new String[]{"Item#12", "6.89"});
        menuItems.put("drinks", drinks);

        ArrayList<String[]> sauces = new ArrayList<>();
        sauces.add(new String[]{"Item#13", "7.91"});
        sauces.add(new String[]{"Item#14", "5.39"});
        menuItems.put("sauces", sauces);
    }
    Cashier()  {
        // frame.add(label);
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tb = new JToolBar();
 
        // create a panel
        JPanel p = new JPanel();

        breakfast = new JButton("Breakfast");
        entree = new JButton("Entree");
        salads = new JButton("Salads");
        sides = new JButton("Sides");
        kids = new JButton("Kids Meals");
        treats = new JButton("Treats");
        drinks = new JButton("Drinks");
        dipping = new JButton("Dipping sauces & Dressings");

        // add buttons
        p.add(breakfast);
        p.add(entree);
        p.add(salads);
        p.add(sides);
        p.add(kids);
        p.add(treats);
        p.add(drinks);
        p.add(dipping);

        frame.add(p, BorderLayout.CENTER);
        frame.add(tb, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}
