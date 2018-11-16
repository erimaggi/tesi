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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static final int WIDTH = 400; 
    private static final int HEIGHT= 200;
    JPanel panel1, panel2, panel3;
    JLabel label1, label2, label3;
    JComboBox comboTree;
    JButton load;
    
    public TreeFrame(){
        setSize(WIDTH, HEIGHT);
        setTitle("TREE");
        this.createFrame();
        this.setResizable(false);
        
    }
    
    private void createFrame(){
        setLayout(new GridLayout(3,1));
        panel1 = new JPanel();
        label1 = new JLabel ("Seleziona l'albero di partenza");
        comboTree = new JComboBox(fillComboBox().toArray());
        load = new JButton("Carica file json");
        setPanel(this, panel1, 1, 1, 1, 3);
        panel1.add(label1);
        panel1.add(comboTree);
        
        panel2 = new JPanel();
        setPanel(this, panel2, 1, 1, 1, 3);
        panel2.add(load);
        load.addActionListener(this);   
        
        panel3 = new JPanel();
        label3 = new JLabel();
        setPanel(this, panel3, 1, 1, 1, 3);
        panel3.add(label3);
           
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
            label3.setFont(new Font(label3.getFont().getFontName(), Font.BOLD, label3.getFont().getSize()));
            if(findJson() != null){
                label3.setText("File json "+findJson()+ " caricato correttamente");                        
                CalculationFrame calc=new CalculationFrame(findJson());
                calc.setVisible(true);
                calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.dispose();
            }else{
                label3.setText("File json non trovato");
            }
            //comboTree.setEnabled(false);
            //load.setEnabled(false);
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
                    //System.out.println(rs.getString("albero_decisionale"));
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
            String query = "SELECT * FROM uceweb_aime2017.jsonFiles WHERE albero_partenza= '"+comboTree.getSelectedItem().toString()+"'";
            ResultSet rs = ldb.getRS(query);
            while(rs.next()){
                if(!rs.getString("nome_file").equals(null)){
                   jsonName = rs.getString("nome_file");
                }
            }
            //jsonName = comboTree.getSelectedItem().toString();
        } catch (SQLException ex) {
            System.out.println("SQLException");
        }
        return jsonName;
    }
    
    
}
