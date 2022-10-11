import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Manager implements ActionListener {
    
    // Boilerplate Code
    private JFrame frame;
    private JToolBar tb;
    static JButton inventoryButton, menuButton;
    CardLayout cardLayout;
    JPanel cardPanel;

    Manager() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tb = new JToolBar();

        // create panel to hold function switches
        JPanel functionPanel = new JPanel();

        // Setting up panel with the frame and displaying frame
        // frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Manager Launchpad");
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if (e.getSource() == inventory_button) {
        //     frame.dispose();
        //     new Inventory();
        // }
        // if (e.getSource() == menu_button) {
        //     frame.dispose();
        //     new MenuEditor();
        // }
    }
}