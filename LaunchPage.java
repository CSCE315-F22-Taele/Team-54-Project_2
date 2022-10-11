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
    JButton cashierButton = new JButton("Cashier");
    JButton managerButton = new JButton("Manager");

    public LaunchPage()
    {
        
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0,1));
        
        cashierButton.addActionListener(this);
        managerButton.addActionListener(this);
        
        panel.add(cashierButton);
        panel.add(managerButton);


        // Setting up panel with the frame and displaying frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        // frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String args[])
    {
        new LaunchPage();
    }

    @Override
<<<<<<< HEAD
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cashierButton) {
=======
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == cashier_button) {
>>>>>>> c6003b560fc1b9a9905604ff068f0ae384d20374
            frame.dispose();
            new Cashier();
        }
        if (e.getSource() == managerButton) {
            frame.dispose();
            new ManagerLaunch();
        }
    }
}