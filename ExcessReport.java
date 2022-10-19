import java.awt.*;
import javax.swing.*;

public class ExcessReport {
    
    /**
     * Default constructor
     */
    ExcessReport() {}

    /**
     * Creates GUI view for excess report over a given time interval
     * @param startDate the starting date of the time interval to view
     * @param endDate the ending date of the the time interval to view
     */
    ExcessReport(String startDate, String endDate) {
        JFrame frame = new JFrame();
        frame.add(report(startDate, endDate));

        // set basic frame dimensions/characteristics
        frame.setTitle("Excess Report");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    /**
     * Generates report of excess inventory based on the inventory's state over the given time interval
     * @param startDate the starting date of the time interval over which to view excess
     * @param endDate the ending date of the time interval
     * @return a JPanel containing a table of items in excess
     */
    private JPanel report(String startDate, String endDate) {
        JPanel excessPanel = new JPanel(new BorderLayout());

        // Generates JTable that pulls inventory excess from SQL table
        String[] colNames = {"Name",
                             "Category",
                             "Quantity",
                             "Unit"};

        String[][] excessData = Backend.excessView(startDate, endDate);
        JTable excess = new JTable(excessData, colNames);
        excess.setFillsViewportHeight(true);

        excessPanel.add(new JScrollPane(excess), BorderLayout.CENTER);

        return excessPanel;
    }
}