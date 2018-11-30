/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplicationtree.UseObjectInterface;
import static javafx.beans.binding.Bindings.concat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import static javax.swing.SwingConstants.CENTER;
import jsonReader.JsonReader;

/**
 *
 * @author quaglini
 */
public class CalculationFrame extends JFrame {
    
    private static final int WIDTH = 600; 
    private static final int HEIGHT= 100;
    String newName = "/home/quaglini/treeage/workspace/newTree.trex";
    String newName2 = "/home/quaglini/treeage/workspace/newTree2.trex";
    String path = "/home/quaglini/Desktop/java/JavaApplicationTree/fileDati/";
    String expVal, varName;
    Map<String, String> mapp;
    JsonReader js;
    UseObjectInterface oInt;
    int numPayoffs;
    String[] payoffName, strategies;
    JPanel panel1;
    JLabel label1;
    HashMap <Integer, Mapping> panels = new HashMap();
    private static final int SIMPLE_ROLLBACK_INT = 0;
    
    public CalculationFrame(String file){
        mapp = new HashMap<>();
        js = new JsonReader("fileDati/"+file);
        js.readJson();
        oInt = new UseObjectInterface();
        oInt.openTree(path+js.getTree("firstTree"));      
        oInt.copyTree(newName);
        String [] elements = oInt.getPayoffs().split(";");
        System.out.println(elements[1]);
        numPayoffs = Integer.parseInt(elements[0]);
        payoffName = elements[1].split("[|]");
        strategies = oInt.getStrategies().split("[|]");
        System.out.println(strategies[1]+" "+ strategies[2]);
        setSize(WIDTH, HEIGHT + 100*numPayoffs);
        setTitle("Rollback");
        this.createFrame();
        this.setResizable(false); 
        
    }
    
    private void createFrame(){
        setLayout(new GridLayout(1+numPayoffs,1));
        
        panel1 = new JPanel();
        label1 = new JLabel("Risultati analisi decisionale");
        label1.setFont(new Font(label1.getFont().getFontName(), Font.BOLD, label1.getFont().getSize()));
        label1.setForeground(Color.red);
        label1.setHorizontalAlignment(CENTER);
        setPanel(this, panel1, 1, 1, 1, 10);
        panel1.add(label1);
        
        for (int i = 0; i<numPayoffs; i++){   
            panels.put(i+1, new Mapping(i+1,payoffName[i], strategies));
            //panels.add(new JPanel());
            //labels.add(new JLabel(payoffName[i]));
          
            setPanel(this, panels.get(i+1).getPanel(), 1, 1, 1, 10);
            panels.get(i+1).getPanel().add(panels.get(i+1).getButton());
            
            final int buttonIndex = i+1;

            
            panels.get(i+1).getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button pressed is: " + buttonIndex);
                    if(e.getActionCommand().equals(panels.get(buttonIndex).getButton().getText())){
                        catchVars(buttonIndex);
                        putVars();
                        try {
                            String[] vals = oInt.analyzeTree(SIMPLE_ROLLBACK_INT, buttonIndex).split("[|]");
                            panels.get(buttonIndex).getLabel1().setText(strategies[1]+" = "+String.format("%.03f", Float.valueOf(vals[1])));
                            panels.get(buttonIndex).getLabel2().setText(strategies[2]+" = " +String.format("%.03f",  Float.valueOf(vals[2])));
                            oInt.closeTree();
                        } catch (RemoteException ex) {
                            Logger.getLogger(CalculationFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        mapp.clear();
                        System.gc();
            }
                    
                }
            });
            panels.get(i+1).getPanel().add(panels.get(i+1).getLabel1());
            panels.get(i+1).getPanel().add(panels.get(i+1).getLabel2());
          
            panels.get(i+1).getButton().setPreferredSize(new Dimension(panels.get(i+1).getPanel().getWidth() * 12 / 40, panels.get(i+1).getPanel().getHeight()));
            panels.get(i+1).getLabel1().setPreferredSize(new Dimension(panels.get(i+1).getPanel().getWidth() * 12 / 40, panels.get(i+1).getPanel().getHeight())); 
            panels.get(i+1).getLabel2().setPreferredSize(new Dimension(panels.get(i+1).getPanel().getWidth() * 12 / 40, panels.get(i+1).getPanel().getHeight())); 
        }
    }
    
    private void setPanel(Container con, JPanel panel, int num1, int den1, int num2, int den2) {
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension((int) con.getSize().getWidth() * num1 / den1, (int) con.getSize().getHeight() * num2 / den2));
        panel.setSize(new Dimension((int) con.getSize().getWidth() * num1 / den1, (int) con.getSize().getHeight() * num2 / den2));
        panel.setMaximumSize(new Dimension((int) con.getSize().getWidth() * num1 / den1, (int) con.getSize().getHeight() * num2 / den2));
        con.add(panel);
    }

//    public void actionPerformed(ActionEvent e) {
//        for(int j = 0; j<numPayoffs; j++){
//            if(e.getActionCommand().equals(panels.get(j+1).getButton().getText())){
////                if(e.getActionCommand().equals("Rollback QALM")){
////                    System.out.println("a");
////                }
//                panels.get(j+1).getLabel1().setText("ciao");
//                catchVars(j+1);
//                break;
//            }
//        }
//    }
    
    private void catchVars(int index){
        try {
            if(!oInt.isOpen(newName)){
                oInt.openTree(newName);
            }
            oInt.getTree().setCalculationMethod(SIMPLE_ROLLBACK_INT, index, index);
            for (int i=0; i<js.readNode("payoff"+Integer.toString(index)).size(); i++){
                expVal = oInt.selectNode(js.readNode("payoff"+Integer.toString(index)).get(i));
                varName = js.readVars("payoff"+Integer.toString(index)).get(i);
                System.out.println(varName+" = "+expVal);
                //mappa che contiene i valori degli initial da mettere nel secondo albero
                mapp.put(varName, expVal);
//            varName = null;
//            expVal = null;
System.out.println("-");
//System.out.println(mapp.toString());
            }
            oInt.closeTree();
        } catch (RemoteException ex) {
            Logger.getLogger(CalculationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void putVars(){
        try {
            oInt.openTree(path+js.getTree("secondTree"));
            oInt.copyTree(newName2);
            Set<String> keys = mapp.keySet();
            for(String key: keys){
                System.out.println("*****************sostituzione var secondo albero*************");
                oInt.updateVariable(key, mapp.get(key));
            }
            oInt.getTree().save();
        } catch (RemoteException ex) {
            Logger.getLogger(CalculationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
}
