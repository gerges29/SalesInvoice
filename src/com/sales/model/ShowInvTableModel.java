
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ShowInvTableModel extends AbstractTableModel{
    private ArrayList<InvoiceTable> inv;
    private String[] columns = {"Number", "Date", "Name", "Invoice Total"};

    public ShowInvTableModel(ArrayList<InvoiceTable> inv) {
        this.inv = inv;
    }
    

    @Override
    public int getRowCount() {
            return inv.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        InvoiceTable invoice = inv.get(row);
        
        switch (column){
            case 0:
                return invoice.getNumber();
            case 1:
                return invoice.getDate();
            case 2:
                return invoice.getName();
            case 3:
                return invoice.getInvoiceTotal();
            default:
                return "";
        }
    }   
}
