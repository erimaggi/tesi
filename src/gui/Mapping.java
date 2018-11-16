/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author quaglini
 */
public class Mapping {
    
    private JPanel panel;
    private JLabel label1, label2;
    private JButton button;
    private int index;

    public Mapping(int index, String payoffName, String[] strategies) {
        this.index = index;
        panel = new JPanel();
        label1 = new JLabel(strategies[1]+"= ...");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label2 = new JLabel(strategies[2]+"= ...");
        label2.setHorizontalAlignment(JLabel.CENTER);
        button = new JButton("Rollback "+payoffName);
        button.setHorizontalAlignment(JLabel.CENTER);
    }


    public JPanel getPanel() {
        return panel;
    }

    public JLabel getLabel1() {
        return label1;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JButton getButton() {
        return button;
    }

    public int getIndex() {
        return index;
    }
    
    
    
}
