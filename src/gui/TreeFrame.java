/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dbConn.LoaderDB;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author erikamaggi
 */
public class TreeFrame extends JFrame  implements ActionListener{
    
    public static final int WIDTH = 600; 
    public static final int HEIGHT= 600;
    JPanel panel1, panel2, panel3;
    JLabel label1, label2, label3;
    JComboBox comboTree;
    JButton load, rollback;
    
    public TreeFrame(){
        setSize(WIDTH, HEIGHT);
        setTitle("TREE");
        this.createFrame();
        
    }
    
    private void createFrame(){
        setLayout(new GridLayout(10,1));
        panel1 = new JPanel();
        label1 = new JLabel ("Seleziona l'albero decisionale di partenza");
        //panel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        //String[] treeStrings = { "...", "prova.trex", "prova2.trex" };
        comboTree = new JComboBox(fillComboBox().toArray());
        load = new JButton("Carica file json");
        setPanel(this, panel1, 1, 1, 1, 10);
        panel1.add(label1);
        panel1.add(comboTree);
        panel1.add(load);
        load.addActionListener(this);
        
        panel2 = new JPanel();
        label2 = new JLabel();
        setPanel(this, panel2, 1, 1, 1, 10);
        panel2.add(label2);
        
        panel3 = new JPanel();
        rollback = new JButton("Rollback");
        setPanel(this, panel3, 1, 1, 1, 10);
        panel3.add(rollback);
        rollback.addActionListener(this);
        
    
    }

    private void setPanel(Container con, JPanel panel, int num1, int den1, int num2, int den2) {
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension((int) con.getSize().getWidth() * num1 / den1, (int) con.getSize().getHeight() * num2 / den2));
        panel.setSize(new Dimension((int) con.getSize().getWidth() * num1 / den1, (int) con.getSize().getHeight() * num2 / den2));
        panel.setMaximumSize(new Dimension((int) con.getSize().getWidth() * num1 / den1, (int) con.getSize().getHeight() * num2 / den2));
        con.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Carica file json")){
            label2.setFont(new Font(label2.getFont().getFontName(), Font.BOLD, label2.getFont().getSize()));
            label2.setText("File json "+findJson()+ " caricato correttamente");
            comboTree.setEnabled(false);
        }
    }

    private ArrayList fillComboBox() {
        String[] treeStrings = null;
        ArrayList<String> trees = new ArrayList<String>();
        try {
            LoaderDB ldb = new LoaderDB();
            ResultSet rs = ldb.getRS("SELECT albero_decisionale FROM uceweb_aime2017.albero_decisionale");
            while(rs.next()){
                if(!rs.getString("albero_decisionale").equals(null)){
                    trees.add(rs.getString("albero_decisionale"));
                    System.out.println(rs.getString("albero_decisionale"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException");
        }
        return trees;
    }
    
    private String findJson() {
        String jsonName = null;
        try {
            LoaderDB ldb = new LoaderDB();
            System.out.println(comboTree.getSelectedItem().toString());
            //ResultSet rs = ldb.getRS("SELECT * FROM uceweb_aime2017.jsonFiles WHERE albero_partenza="+comboTree.getSelectedItem().toString());
            //jsonName = rs.getString("albero_partenza");
            jsonName = comboTree.getSelectedItem().toString();
        } catch (SQLException ex) {
            System.out.println("SQLException");
        }
        return jsonName;
    }
    
    
}
