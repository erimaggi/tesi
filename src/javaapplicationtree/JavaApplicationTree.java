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
//        try {
            // TODO code application logic here
            
            //lettura file json
//            String file = "fileDati/tree1.json";
//            
//            JsonReader js = new JsonReader(file);
//            js.readJson();
//            js.printElement("firstTree");
//            
            
            
            
            //prova connessione a treeagepro
            
//            UseObjectInterface oInt = new UseObjectInterface();
//            //oInt.openTree("prova4aprile2013.trex");
//            oInt.openTree("prometeo/home/cl416968/Desktop/proveTreeage/provaSenzaData.trex");
//            oInt.copyTree();
//            try {
//                oInt.outputTreeInfo();
//            } catch (RemoteException ex) {
//                System.out.println("Connection to TreeAge Pro application is lost.");
//            }
//            oInt.updateVariable("cTest");
//            try {
//                oInt.analyzeTree();
//            } catch (RemoteException ex) {
//                Logger.getLogger(JavaApplicationTree.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            oInt.getAllBranches();
//            
//            
//        } catch (RemoteException ex) {
//            Logger.getLogger(JavaApplicationTree.class.getName()).log(Level.SEVERE, null, ex);
//        }

        
    }
    
}
