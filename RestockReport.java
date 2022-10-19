import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class RestockReport implements TableModelListener {
    
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
    
    private JPanel report() {
        JPanel p = new JPanel(new BorderLayout());

        String[] colNames = {"Item ID",
                             "Name",
                             "Category",
                             "Expiration Date",
                             "Refrigeration Required",
                             "Quantity",
                             "Unit"};

        String[][] restockData = Backend.restockView();
        JTable t = new JTable(restockData, colNames);
        t.getModel().addTableModelListener(this);
        t.setFillsViewportHeight(true);

        p.add(new JScrollPane(t), BorderLayout.CENTER);

        return p;
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