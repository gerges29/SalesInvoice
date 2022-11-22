
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ShowLinTableModel extends AbstractTableModel {
    
    private ArrayList<LineTable> lin;
    private String[] columns = {"Number", "Section", "Item Price", "Line Total"};

    public ShowLinTableModel(ArrayList<LineTable> lin) {
        this.lin = lin;
    }

    public ArrayList<LineTable> getLines() {
        return lin;
    }
    
    @Override
    public int getRowCount() {
        return lin.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int z) {
        return columns[z];
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LineTable line = lin.get(rowIndex);
        
        switch (columnIndex){
            case 0: return line.getInvoice().getNumber();
            case 1: return line.getSection();
            case 2: return line.getItemPrice();
            case 3: return line.getQuantity();
            case 4: return line.getLineTotal();
            default:return "";
        }
    }
    
    
}
