/**
 * This class creates the launch page, which is the first page that the GUI
 * opens upon launching. It redirects between cashier and manager.
 * @author Neha Sujith
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LaunchPage implements ActionListener {

    // Boilerplate Code
    private JFrame frame;
    private JPanel panel;
    JButton cashierButton = new JButton("Cashier");
    JButton managerButton = new JButton("Manager");

    public LaunchPage() {
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
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String args[])
    {
        new LaunchPage();
    }

     /**
      * opens Cashier or Manager view based on user button choice
      * @param e  action performed by the user
      */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cashierButton) {
            frame.dispose();
            new Cashier();
        }
        if (e.getSource() == managerButton) {
            frame.dispose();
            new Manager();
        }
    }
}
