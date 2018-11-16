/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationtree;

import com.treeage.treeagepro.oi.TreeAgeProApplication;
import gui.TreeFrame;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


/**
 *
 * @author erikamaggi
 */
public class JavaApplicationTree {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TreeFrame f=new TreeFrame();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
    }
    
}
