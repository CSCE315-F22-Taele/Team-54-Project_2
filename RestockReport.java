/**
 * This class creates the restock report, which displays the list of items whose
 * inventory is less than the item's minimum amount before needing to restock.
 * @author Estella Chen
 */
import java.awt.*;
import javax.swing.*;

public class RestockReport {
    
    /**
     * Creates default Restock GUI view with basic frame dimensions/characteristics
     */
    RestockReport() {
        JFrame frame = new JFrame();
        frame.add(report());

        // set basic frame dimensions/characteristics
        frame.setTitle("Restock Report");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    /**
     * Creates the panel to display the restock report. Generates a JTable that pulls depleted items from
     * the SQL inventory table.
     * @return a JPanel displaying the items that need to be restocked.
     */
    private JPanel report() {
        JPanel p = new JPanel(new BorderLayout());

        // Generate data and make JTable
        String[] colNames = {"Item ID",
                             "Name",
                             "Category",
                             "Expiration Date",
                             "Refrigeration Required",
                             "Quantity",
                             "Unit"};

        String[][] restockData = Backend.restockView();
        JTable t = new JTable(restockData, colNames);
        t.setFillsViewportHeight(true);

        // Add table to scrollable panel
        p.add(new JScrollPane(t), BorderLayout.CENTER);

        return p;
    }
}