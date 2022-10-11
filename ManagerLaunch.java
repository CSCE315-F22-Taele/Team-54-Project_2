import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ManagerLaunch implements ActionListener {
    
    // Boilerplate Code
    private JFrame frame;
    private JPanel panel;
    JButton inventory_button = new JButton("Inventory");
    JButton menu_button = new JButton("Edit Menu");

    ManagerLaunch() {
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0,1));;

        // add event listeners
        inventory_button.addActionListener(this);
        menu_button.addActionListener(this);

        panel.add(inventory_button);
        panel.add(menu_button);

        // Setting up panel with the frame and displaying frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Manager Launchpad");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inventory_button) {
            frame.dispose();
            // new Inventory();
        }
        if (e.getSource() == menu_button) {
            frame.dispose();
            // new MenuEditor();
        }
    }
}