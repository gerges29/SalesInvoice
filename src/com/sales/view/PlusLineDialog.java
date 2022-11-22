
package com.sales.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class PlusLineDialog extends JDialog{
    private JTextField itemNameField;
    private JTextField itemQuantityField;
    private JTextField itemCostField;
    private JLabel itemNameLabel;
    private JLabel itemQuantityLabel;
    private JLabel itemCostLabel;
    private JButton doneButton;
    private JButton cancelButton;
    
    public PlusLineDialog(InvoiceDesign design) {
        itemNameField = new JTextField(20);
        itemNameLabel = new JLabel("Item Name");
        
        itemQuantityField = new JTextField(20);
        itemQuantityLabel = new JLabel("Item Quantity");
        
        itemCostField = new JTextField(20);
        itemCostLabel = new JLabel("Item Cost");
        
        doneButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        doneButton.setActionCommand("LineDone");
        cancelButton.setActionCommand("LineCancel");
        
        doneButton.addActionListener(design.getController());
        cancelButton.addActionListener(design.getController());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabel);
        add(itemNameField);
        add(itemQuantityLabel);
        add(itemQuantityField);
        add(itemCostLabel);
        add(itemCostField);
        add(doneButton);
        add(cancelButton);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemQuantityField() {
        return itemQuantityField;
    }

    public JTextField getItemCostField() {
        return itemCostField;
    }
}
