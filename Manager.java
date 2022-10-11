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

        functionPanel.add(inventoryButton);
        functionPanel.add(menuButton);
        functionPanel.add(trendsButton);

        inventoryButton.addActionListener(this);
        menuButton.addActionListener(this);
        trendsButton.addActionListener(this);

        // Setting up panel with the frame and displaying frame
        frame.add(functionPanel, BorderLayout.PAGE_START);
        frame.add(tb, BorderLayout.NORTH);

        // make cards for switching logic and add actual material to each view
        cardLayout = new CardLayout();
        cardPanel = new JPanel();

        cardPanel.add(inventoryPanel(), "inventory");
        cardPanel.add(menuPanel(), "menu editor");
        cardPanel.add(trendsPanel(), "trends");

        // set basic frame dimensions/characteristics
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Manager GUI");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inventoryButton) {
            cardLayout.show(cardPanel, "inventory");
        } else if (e.getSource() == menuButton) {
            cardLayout.show(cardPanel, "menu editor");
        } else if (e.getSource() == trendsButton) {
            cardLayout.show(cardPanel, "trends");
        }
    }

    /*
     * Functions to create panels for each functionality: inventory, menu editing, and trends
     */
    private JPanel inventoryPanel() {
        JPanel inventoryPanel = new JPanel();

        return inventoryPanel;
    }

    private JPanel menuPanel() {
        JPanel menuPanel = new JPanel();

        return menuPanel;
    }

    private JPanel trendsPanel() {
        JPanel trendsPanel = new JPanel ();

        return trendsPanel;
    }
}