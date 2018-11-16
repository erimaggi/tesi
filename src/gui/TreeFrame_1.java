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
public class TreeFrame_1 extends JFrame  implements ActionListener{
    
    public static final int WIDTH = 600; 
    public static final int HEIGHT= 600;
    JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel10;
    JLabel label1, label2, label3, label4, labelTLM, labelTORS, labelEmpty, label5, label5TORS, label5TLM, label6, label6TORS, label6TLM, label7, label7TORS, label7TLM, label10, label10ind, labelEmpty2, labelEmpty3, labelEmpty4;
    JComboBox comboTree;
    JButton load, rollback, indici;
    
    public TreeFrame_1(){
        setSize(WIDTH, HEIGHT);
        setTitle("TREE");
        this.createFrame();
        
    }
    
    private void createFrame(){
        setLayout(new GridLayout(10,1));
        panel1 = new JPanel();
        label1 = new JLabel ("Seleziona l'albero decisionale di partenza");
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
        
        panel4 = new JPanel();
        label4 = new JLabel("Risultati");
        //label4.setHorizontalAlignment(JLabel.CENTER);
        label4.setFont(new Font(label4.getFont().getFontName(), Font.BOLD, label4.getFont().getSize()));
        label4.setForeground(Color.red);
        labelTLM = new JLabel("TLM");
        labelTLM.setHorizontalAlignment(JLabel.CENTER);
        labelTORS = new JLabel("TORS");
        labelTORS.setHorizontalAlignment(JLabel.CENTER);
        labelEmpty = new JLabel();
        
        setPanel(this, panel4, 1, 1, 1, 10);
        panel4.add(label4);
        panel4.add(labelEmpty);
        panel4.add(labelTLM);
        panel4.add(labelTORS);
        label4.setPreferredSize(new Dimension(panel4.getWidth() * 10 / 40, panel4.getHeight()));
        labelEmpty.setPreferredSize(new Dimension(panel4.getWidth() * 5 / 40, panel4.getHeight()));
        labelTLM.setPreferredSize(new Dimension(panel4.getWidth() * 10 / 40, panel4.getHeight()));
        labelTORS.setPreferredSize(new Dimension(panel4.getWidth() * 10 / 40, panel4.getHeight())); 
        
        
        panel5 = new JPanel();
        label5 = new JLabel("QALMS");
        label5TLM = new JLabel("-");
        label5TLM.setHorizontalAlignment(JLabel.CENTER);
        label5TORS = new JLabel("-");
        label5TORS.setHorizontalAlignment(JLabel.CENTER);
        labelEmpty2 = new JLabel();
        
        setPanel(this, panel5, 1, 1, 1, 10);
        panel5.add(label5);
        panel5.add(labelEmpty2);
        panel5.add(label5TLM);
        panel5.add(label5TORS);
        label5.setPreferredSize(new Dimension(panel5.getWidth() * 10 / 40, panel5.getHeight()));
        labelEmpty2.setPreferredSize(new Dimension(panel5.getWidth() * 5 / 40, panel5.getHeight()));
        label5TLM.setPreferredSize(new Dimension(panel5.getWidth() * 10 / 40, panel5.getHeight()));
        label5TORS.setPreferredSize(new Dimension(panel5.getWidth() * 10 / 40, panel5.getHeight()));
        
        panel6 = new JPanel();
        label6 = new JLabel("YEARS");
        label6TLM = new JLabel("-");
        label6TLM.setHorizontalAlignment(JLabel.CENTER);
        label6TORS = new JLabel("-");
        label6TORS.setHorizontalAlignment(JLabel.CENTER);
        labelEmpty3 = new JLabel();
        
        setPanel(this, panel6, 1, 1, 1, 10);
        panel6.add(label6);
        panel6.add(labelEmpty3);
        panel6.add(label6TLM);
        panel6.add(label6TORS);
        label6.setPreferredSize(new Dimension(panel6.getWidth() * 10 / 40, panel6.getHeight()));
        labelEmpty3.setPreferredSize(new Dimension(panel6.getWidth() * 5 / 40, panel6.getHeight()));
        label6TLM.setPreferredSize(new Dimension(panel6.getWidth() * 10 / 40, panel6.getHeight()));
        label6TORS.setPreferredSize(new Dimension(panel6.getWidth() * 10 / 40, panel6.getHeight()));
        
        panel7 = new JPanel();
        label7 = new JLabel("COSTS");
        label7TLM = new JLabel("-");
        label7TLM.setHorizontalAlignment(JLabel.CENTER);
        label7TORS = new JLabel("-");
        label7TORS.setHorizontalAlignment(JLabel.CENTER);
        labelEmpty4 = new JLabel();
        
        setPanel(this, panel7, 1, 1, 1, 10);
        panel7.add(label7);
        panel7.add(labelEmpty4);
        panel7.add(label7TLM);
        panel7.add(label7TORS);
        label7.setPreferredSize(new Dimension(panel7.getWidth() * 10 / 40, panel7.getHeight()));
        labelEmpty4.setPreferredSize(new Dimension(panel7.getWidth() * 5 / 40, panel7.getHeight()));
        label7TLM.setPreferredSize(new Dimension(panel7.getWidth() * 10 / 40, panel7.getHeight()));
        label7TORS.setPreferredSize(new Dimension(panel7.getWidth() * 10 / 40, panel7.getHeight()));
        
        panel8 = new JPanel();
        setPanel(this, panel8, 1, 1, 1, 10);
        
        panel9 = new JPanel();
        indici = new JButton("Calcola indici");
        setPanel(this, panel9, 1, 1, 1, 10);
        panel9.add(indici);
        indici.addActionListener(this);
        
        panel10 = new JPanel();
        label10 = new JLabel("ICER/ICUR");
        label10ind = new JLabel("-");
        
        setPanel(this, panel10, 1, 1, 1, 10);
        panel10.add(label10);
        panel10.add(label10ind);
        label10.setPreferredSize(new Dimension(panel10.getWidth() * 12 / 20, panel10.getHeight()));
        label10ind.setPreferredSize(new Dimension(panel10.getWidth() * 7 / 20, panel10.getHeight()));
    
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
            load.setEnabled(false);
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
