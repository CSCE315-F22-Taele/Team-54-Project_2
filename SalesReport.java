import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SalesReport implements ActionListener, TableModelListener {

    SalesReport(String startDate, String endDate) {
        frame = new JFrame();
        frame.add(report(startDate, endDate));

        // set basic frame dimensions/characteristics
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sales Report GUI");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel report(String startDate, String endDate) {
        JPanel salesPanel = new JPanel(new BorderLayout());

        String[] colNames = {"Order ID",
                             "Order Number",
                             "Total Price Due",
                             "Date",
                             "Employee ID",
                             "Customer ID",
                             "Order Satisfied",
                             "Items Ordered"};


        JTable sales = new JTable(saleData, colNames);
        sales.getModel().addTableModelListener(this);

        String[][] saleData = Backend.salesView(startDate, endDate);
        sales.setFillsViewportHeight(true);

        salesPanel.add(new JScrollPane(sales), BorderLayout.CENTER);

        return salesPanel;
    }
}