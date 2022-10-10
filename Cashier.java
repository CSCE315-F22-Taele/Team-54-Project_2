import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Cashier extends JFrame {
    
    JFrame frame = new JFrame();
    static JToolBar tb;
    static JButton breakfast, entree, salads, sides, kids, treats, drinks, dipping;

    Cashier() {
        // frame.add(label);
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

        frame.add(tb, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}
