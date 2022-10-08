// import java.awt.LayoutManager;

// import javax.swing.BorderFactory;
// import javax.swing.JFrame;
// import javax.swing.JPanel;
import java.awt.*;    
import javax.swing.*;
import java.awt.event.*;

public class GUI implements ActionListener{
    
    private int count = 0;
    
    // Boilerplate Code
    private JLabel label;
    private JFrame frame;
    private JPanel panel;

    public GUI()
    {
        
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0,1));
        
        
        
        JButton button = new JButton("Click Me");
        button.addActionListener(this);
        label = new JLabel("Number of Clicks: 0");
        
        panel.add(button);
        panel.add(label);


        // Setting up panel with the frame and displaying frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cashier GUI");
        frame.pack();
        frame.setVisible(true);
    }





    public static void main(String args[])
    {
        new GUI();
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        count++;
        label.setText("Number of clicks: " + count);
        
    }
}
