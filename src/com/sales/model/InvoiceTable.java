
package com.sales.model;

import java.util.ArrayList;

public class InvoiceTable {
    private int number;
    private String name;
    private String date;
    private ArrayList<LineTable> lines;
    
    
    public InvoiceTable() {
    }

    public InvoiceTable(int number, String name, String date) {
        this.number = number;
        this.name = name;
        this.date = date;
    }
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
     public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public double getInvoiceTotal(){
        double total =0.0;
        for (LineTable line : getLines()){
            total += line.getLineTotal();
        }
        return total;
    }

    public ArrayList<LineTable> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    @Override
    public String toString() {
        return "Invoice{" + "number=" + number + ", date=" + date + ", name=" + name + '}';
    }
    
    public String getAsFile() {
        return number + "," + date + "," + name;
    }
    
}
