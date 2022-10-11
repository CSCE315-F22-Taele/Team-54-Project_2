// import java.awt.LayoutManager;

// import javax.swing.BorderFactory;
// import javax.swing.JFrame;
// import javax.swing.JPanel;
import java.awt.*;    
import javax.swing.*;
import java.awt.event.*;

public class LaunchPage implements ActionListener {
    
    // Boilerplate Code
    private JFrame frame;
    private JPanel panel;
    JButton cashier_button = new JButton("Cashier");
    JButton manager_button = new JButton("Manager");

    public LaunchPage()
    {
        
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0,1));
        
        cashier_button.addActionListener(this);
        manager_button.addActionListener(this);
        
        panel.add(cashier_button);
        panel.add(manager_button);


        // Setting up panel with the frame and displaying frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String args[])
    {
        new LaunchPage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cashier_button) {
            frame.dispose();
            new Cashier();
        }
        if (e.getSource() == manager_button) {
            frame.dispose();
            new ManagerLaunch();
        }
    }
}