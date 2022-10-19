import java.awt.*;
import javax.swing.*;

public class SalesReport {
    
    /**
     * Default constructor
     */
    SalesReport() {}

    /**
     * Creates sales report view based on given start and end data interval.
     * @param startDate the date to start pulling reports from
     * @param endDate the end date of the time report
     */
    SalesReport(String startDate, String endDate) {
        JFrame frame = new JFrame();
        frame.add(report(startDate, endDate));

        // set basic frame dimensions/characteristics
        frame.setTitle("Sales Report");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    /**
     * Creates JPanel to show the sales report with a JTable that pulls the data
     * from the SQL tables
     * @param startDate the starting date of the time interval to pull data from
     * @param endDate the ending date of the time interval to pull data from
     * @return a JPanel showing a report of sales from the specified time interval
     */
    private JPanel report(String startDate, String endDate) {
        JPanel salesPanel = new JPanel(new BorderLayout());

        // Create JTable with sale data pulled from SQL table
        String[] colNames = {"Order ID",
                             "Order Number",
                             "Total Price Due",
                             "Date",
                             "Employee ID",
                             "Customer ID",
                             "Order Satisfied",
                             "Items Ordered"};


        String[][] saleData = Backend.salesView(startDate, endDate);
        JTable sales = new JTable(saleData, colNames);
        sales.setFillsViewportHeight(true);

        salesPanel.add(new JScrollPane(sales), BorderLayout.CENTER);

        return salesPanel;
    }
}