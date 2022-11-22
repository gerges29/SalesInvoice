package com.sales.controller;

import com.sales.model.InvoiceTable;
import com.sales.model.ShowInvTableModel;
import com.sales.model.LineTable;
import com.sales.model.ShowLinTableModel;
import com.sales.view.PlusInvoiceDialog;
import com.sales.view.InvoiceDesign;
import com.sales.view.PlusLineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller  implements ActionListener, ListSelectionListener {

    private InvoiceDesign design;
    private PlusInvoiceDialog plusInvoiceDialog;
    private PlusLineDialog plusLineDialog;

    public Controller(InvoiceDesign design) {
        this.design = design;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Open File":
                openFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create Item":
                createItem();
                break;
            case "LineCancel":
                lineCancel();
                break;
            case "LineDone":
                lineDone();
                break;
            case "Create Invoice":
                createInvoice();
                break;
            case "InvoiceCancel":
                invoiceCancel();
                break;
            case "InvoiceDone":
                invoiceDone();
                break;
            case "Remove Item":
                removeItem();
                break;
            case "Remove Invoice":
                removeInvoice();
                break;  
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndictor = design.getInvoiceTable().getSelectedRow();
        if (selectedIndictor != -1) {
            System.out.println("You have selected row: " + selectedIndictor);
            InvoiceTable currentInvoice = design.getInvoices().get(selectedIndictor);
            design.getInvoiceNumberLabel().setText("" + currentInvoice.getNumber());
            design.getInvoiceDateLabel().setText(currentInvoice.getDate());
            design.getCustNameLbl().setText(currentInvoice.getName());
            design.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            ShowLinTableModel linesTableModel = new ShowLinTableModel(currentInvoice.getLines());
            design.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        try {
            int result = chooser.showOpenDialog(design);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = chooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
 
                
                ArrayList<InvoiceTable> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        InvoiceTable invoice = new InvoiceTable(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(design, "Error in line", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Check point");
                result = chooser.showOpenDialog(design);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = chooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String lineLine : lineLines) {
                        try {
                        String lineParts[] = lineLine.split(",");
                        int invoiceNum = Integer.parseInt(lineParts[0]);
                        String itemName = lineParts[1];
                        double itemPrice = Double.parseDouble(lineParts[2]);
                        int count = Integer.parseInt(lineParts[3]);
                        InvoiceTable inv = null;
                        for (InvoiceTable invoice : invoicesArray) {
                            if (invoice.getNumber() == invoiceNum) {
                                inv = invoice;
                                break;
                            }
                        }

                            LineTable line = new LineTable(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(design, "Error in line", "Error", JOptionPane.ERROR_MESSAGE);
                    } 
                    }
                    System.out.println("Check point");
                }
                design.setInvoices(invoicesArray);
                ShowInvTableModel invoicesTableModel = new ShowInvTableModel(invoicesArray);
                design.setInvoicesTableModel(invoicesTableModel);
                design.getInvoiceTable().setModel(invoicesTableModel);
                design.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(design, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<InvoiceTable> invoices = design.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceTable invoice : invoices) {
            String invFile = invoice.getAsFile();
            headers += invFile;
            headers += "\n";

            for (LineTable line : invoice.getLines()) {
                String lineFile = line.getAsFile(); 
                lines += lineFile;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(design);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = chooser.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = chooser.showSaveDialog(design);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = chooser.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createInvoice() {
        plusInvoiceDialog = new PlusInvoiceDialog(design);
        plusInvoiceDialog.setVisible(true);
    }

    private void removeInvoice() {
        int selectRow = design.getInvoiceTable().getSelectedRow();
        if (selectRow != -1) {
            design.getInvoices().remove(selectRow);
            design.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createItem() {
        plusLineDialog = new PlusLineDialog(design);
        plusLineDialog.setVisible(true);
    }

    private void removeItem() {
        int selectRow = design.getLineTable().getSelectedRow();

        if (selectRow != -1) {
            ShowLinTableModel linesTableModel = (ShowLinTableModel) design.getLineTable().getModel();
            linesTableModel.getLines().remove(selectRow);
            linesTableModel.fireTableDataChanged();
            design.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void invoiceCancel() {
        plusInvoiceDialog.setVisible(false);
        plusInvoiceDialog.dispose();
        plusInvoiceDialog = null;
    }

    private void invoiceDone() {
        String d = plusInvoiceDialog.getInvoiceDateField().getText();
        String c = plusInvoiceDialog.getCustomerNameField().getText();
        int num = design.getNextInvoiceNumber();
        try {
            String[] dateParts = d.split("-");
            if (dateParts.length < 3){
                JOptionPane.showMessageDialog(design, "Wrong date", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            if (day > 31 || month > 12){
                JOptionPane.showMessageDialog(design, "Wrong date", "Error", JOptionPane.ERROR_MESSAGE);
            } else{
                InvoiceTable inv = new InvoiceTable(num, d, c);
                design.getInvoices().add(inv);
                design.getInvoicesTableModel().fireTableDataChanged();
                plusInvoiceDialog.setVisible(false);
                plusInvoiceDialog.dispose();
                plusInvoiceDialog = null;
            }
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(design, "Wrong date", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void lineDone() {
        String countString = plusLineDialog.getItemQuantityField().getText();
        String priceString = plusLineDialog.getItemCostField().getText();
        String section = plusLineDialog.getItemNameField().getText();
        double price = Double.parseDouble(priceString);
        int count = Integer.parseInt(countString);
        int selectedInvoice = design.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceTable inv = design.getInvoices().get(selectedInvoice);
            LineTable line = new LineTable(section, price, count, inv);
            inv.getLines().add(line);
            ShowLinTableModel LTM = (ShowLinTableModel) design.getLineTable().getModel();
            //linesTableModel.getLines().add(line);
            LTM.fireTableDataChanged();
            design.getInvoicesTableModel().fireTableDataChanged();
        }
        
        plusLineDialog.setVisible(false);
        plusLineDialog.dispose();
        plusLineDialog = null;
    }

    private void lineCancel() {
        plusLineDialog.setVisible(false);
        plusLineDialog.dispose();
        plusLineDialog = null;
    }

}
