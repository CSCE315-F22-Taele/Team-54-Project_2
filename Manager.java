import javax.swing.*;
public class Manager {
    
    JFrame frame = new JFrame();
    JLabel label = new JLabel("This is the manager window");

    Manager() {
        label.setBounds(0, 0, 100, 50);
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.pack();
        frame.setVisible(true);

    }
}