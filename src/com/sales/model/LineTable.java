
package com.sales.model;

public class LineTable {
    private int quantity;
    private String section;
    private double itemPrice;
    private InvoiceTable invoiceTable;

    public LineTable() {
    }

    public LineTable(String section, double itemPrice, int quantity, InvoiceTable invoiceTable) {
        this.section = section;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.invoiceTable = invoiceTable;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
     public double getLineTotal(){
        return itemPrice * quantity;
    }

    @Override
    public String toString() {
        return "Line{" + "number=" + invoiceTable.getNumber() + ", section=" + section + ", itemprice=" + itemPrice + ", quantity=" + quantity + '}';
    }

    public InvoiceTable getInvoice() {
        return invoiceTable;
    }
    
    public String getAsFile() {
        return invoiceTable.getNumber() + "," + section + "," + itemPrice + "," + quantity;
    }
    
    
}
