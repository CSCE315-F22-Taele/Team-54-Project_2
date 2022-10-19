import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ExcessReport implements TableModelListener {
    
    ExcessReport() {}

    ExcessReport(String startDate, String endDate) {
        JFrame frame = new JFrame();
        frame.add(report(startDate, endDate));

        // set basic frame dimensions/characteristics
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Excess Report");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel report(String startDate, String endDate) {
        JPanel excessPanel = new JPanel(new BorderLayout());

        String[] colNames = {"Item ID",
                             "Name",
                             "Category",
                             "Expiration Date",
                             "Refrigeration Required",
                             "Quantity",
                             "Unit"};

        // String[][] excessData = Backend.excessView(startDate, endDate);
        // JTable excess = new JTable(excessData, colNames);
        // excess.getModel().addTableModelListener(this);
        // excess.setFillsViewportHeight(true);

        // excessPanel.add(new JScrollPane(excess), BorderLayout.CENTER);

        return excessPanel;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        // System.out.println("Row: " + row + " Column: " + column);
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        boolean isInv = (model.getColumnCount() == 7);
        boolean isMenu = (model.getColumnCount() == 5);
        System.out.println("Name: " + columnName + "\n" + "Row: " + row + "\n" + "Column: " + column);
    }

}