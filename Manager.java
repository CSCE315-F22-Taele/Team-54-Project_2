import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Manager {
    
    JFrame frame;
    // JLabel label = new JLabel("This is the manager window");

    Manager() {
        // label.setBounds(0, 0, 100, 50);
        // frame.add(label);
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Manager GUI");
        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}