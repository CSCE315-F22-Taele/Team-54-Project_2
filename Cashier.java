import javax.swing.*;
public class Cashier {
    
    JFrame frame = new JFrame();
    JLabel label = new JLabel("This is the cashier window");

    Cashier() {
        label.setBounds(0, 0, 100, 50);
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.pack();
        frame.setVisible(true);

    }
}
