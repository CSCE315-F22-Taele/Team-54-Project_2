import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ExcessReport implements ActionListener, TableModelListener {

    private JFrame frame;

    ExcessReport(String startDate, String endDate) {
        frame = new JFrame();
        frame.add(report(startDate, endDate));

        // set basic frame dimensions/characteristics
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Excess Report GUI");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel report(String startDate, String endDate) {
        JPanel excessPanel = new JPanel(new BorderLayout());

        String[] colNames = {"Order ID",
                             "Order Number",
                             "Total Price Due",
                             "Date",
                             "Employee ID",
                             "Customer ID",
                             "Order Satisfied",
                             "Items Ordered"};



        String[][] saleData = Backend.salesView(startDate, endDate);
        JTable excess = new JTable(saleData, colNames);
        excess.getModel().addTableModelListener(this);
        excess.setFillsViewportHeight(true);

        excessPanel.add(new JScrollPane(excess), BorderLayout.CENTER);

        return excessPanel;
    }
}