/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationtree;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quaglini
 */
public class LaunchingExternalApps{

    public LaunchingExternalApps() {
        Runtime runtime = Runtime.getRuntime();     //getting Runtime object
        String s = "/home/quaglini/Desktop/java/JavaApplicationTree/fileDati/treeAgefile/TreeAgePro2015/TreeAgePro";
        try
        {
            runtime.exec(s); 
            Thread.sleep(15000);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(LaunchingExternalApps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
