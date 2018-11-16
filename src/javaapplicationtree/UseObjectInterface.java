/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationtree;

import com.treeage.treeagepro.oi.AnalysisType;
import com.treeage.treeagepro.oi.Distribution;
import com.treeage.treeagepro.oi.Node;
import com.treeage.treeagepro.oi.Report;
import com.treeage.treeagepro.oi.Tree;
import com.treeage.treeagepro.oi.TreeAgeProApplication;
import com.treeage.treeagepro.oi.Variable;
import com.treeage.treeagepro.oi.VariableDefinition;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quaglini
 */
public class UseObjectInterface {

    private TreeAgeProApplication app;
    private Tree tree;
    
    public UseObjectInterface() {
        //System.setProperty("java.rmi.server.hostname","193.204.34.199");
       // app = new TreeAgeProApplication("193.204.34.199");
//        Connects locally to TreeAge Pro. 
//        For connection to TreeAge Pro on a different computer use TreeAgeProApplication constructor with the host parameter (IP address)
//        such as TreeAgeProApplication(host).
        app = new TreeAgeProApplication();
        if (!app.isValid()) {
            System.out.println("Cannot find TreeAge Pro application running locally.");
            return;
        }else{
            System.out.println("Connect to TreeAge Pro application");
        }
    }  

    
    public void openTree(String fileName) {
        //apertura albero su treeAge
        try {
            tree = app.getTree(fileName);
            if (tree.isValid()) {
                System.out.println("The currently opened top tree is: " + tree.getTreeName());
            } else {
                System.out.println("No one tree document is opened in TreeAge Pro.");
            }
        } catch (RemoteException re) {
            System.out.println("Connection to TreeAge Pro application is lost.");
        }
        
    }
    
    public boolean isOpen(String fileName) {
        boolean isOpen = true;
        try {
            //apertura albero su treeAge
            tree = app.getTree(fileName);
            if (tree.isValid()) {
                System.out.println("The currently opened top tree is: " + tree.getTreeName());
                
            } else {
                System.out.println("No one tree document is opened in TreeAge Pro.");
                isOpen = false;
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(UseObjectInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isOpen;
    }
    
    public void copyTree(String newName) {
        //salvataggio con un nuovo nome
        //String newName = "prometeo/home/cl416968/Desktop/proveTreeage/newTree1.trex";
        
        try {
            tree.saveAs(newName);
            //System.out.println(tree.getFileName());
            if (tree.isValid()) {
                System.out.println("The currently saved tree is: " + tree.getTreeName());
                tree.close();
                openTree(newName);
                tree.save();
            } else {
                System.out.println("No one tree document is saved in TreeAge Pro.");
            }
        } catch (RemoteException re) {
            System.out.println("Connection to TreeAge Pro application is lost.");
        }
    }
    
    public void closeTree() {
        //chiusura ultimo albero
        try {
            tree.close();
            if (tree.isValid()) {
                System.out.println("The currently closed tree is: " + tree.getTreeName());
            } else {
                System.out.println("No one tree document is closed in TreeAge Pro.");
            }
        } catch (RemoteException re) {
            System.out.println("Connection to TreeAge Pro application is lost.");
        }
    }
    
    public void outputTreeInfo() throws RemoteException {

        java.util.List<com.treeage.treeagepro.oi.Variable> varList;
        java.util.List<com.treeage.treeagepro.oi.Tracker> trackerList;
        java.util.List<com.treeage.treeagepro.oi.Table> tableList;
        java.util.List<com.treeage.treeagepro.oi.Distribution> distList;

        try {

            System.out.println("--- Outputting tree characteristics ---");
            System.out.println("File name: " + tree.getFileName());
            System.out.println("Calculation method: " + tree.getCalculationMethod());

            varList = tree.getVariables();
            for(Variable var : varList) {
                System.out.println(var.getName());
            }
            System.out.println("Variable count: " + varList.size());
            

            trackerList = tree.getTrackers();
            System.out.println("Trackers count: " + trackerList.size());

            tableList = tree.getTables();
            System.out.println("Tables count: " + tableList.size());

            distList = tree.getDistributions();
            System.out.println("Distributions count: " + distList.size());

        } catch (RemoteException re) {
            System.out.println("Error outputting tree characteristics.");
            throw re;
        }

    }
    
    public void updateVariable(String varToChange, String newValue) {

        //in questo metodo viene passata la variabile dell'albero da modificare 
        com.treeage.treeagepro.oi.Variable myVar;
        com.treeage.treeagepro.oi.VariableDefinition myVarDef;

        try {

            System.out.println("--- Updating variable props and definition ---");
            myVar = tree.getVariable(varToChange);
            if(myVar==null){
                System.out.println("var null");
            }
            System.out.println("Variable name current: " + myVar.getName());
            System.out.println("Variable desc current: " + myVar.getDescription());

            myVarDef = myVar.getRootDefinition();
            System.out.println("Variable def at root current: " + myVarDef.getValue());
            myVarDef.setValue(newValue);
            System.out.println("Variable def at root new:     " + myVarDef.getValue());
            myVar.setRootDefinition(myVarDef);

            tree.updateVariable(myVar);
            //salvo i cambiamenti apportati alle variabili di interesse
            //tree.save();

        } catch (RemoteException re) {
            try {
                System.out.println("Error updating variable and/or variable definition.");
                throw re;
            } catch (RemoteException ex) {
                System.out.println("Connection to TreeAge Pro application is lost.");
            }
        }

    }
    
        /**
     * Example of updating distribution parameters within a model.
     */
    private void updateDistributions(Tree sampleTree) throws RemoteException {

    	List<Distribution> dists = sampleTree.getDistributions();
        Map<String, String> distParams = new HashMap<String, String>();
        distParams.put("mean", "1,234.5");
        distParams.put("stddev", "76.543");
        // Change parameters for first distribution Index 1 in TreeAge equates to 0th element of the list.
        System.out.println(dists.get(0).getParametersMap().entrySet());
        dists.get(0).setParametersMap(distParams);            
        sampleTree.updateDistributions(dists);
	}
    
        /**
     * Analyze the tree...
     * - Run roll back and output results for each strategy.
     * - Run simulation (PSA for CE tree, trials for simple tree.
     */
    public String analyzeTree(int calcMet, int payoff) throws RemoteException {

        com.treeage.treeagepro.oi.Node myNode, node1;
        com.treeage.treeagepro.oi.Node stratNode;
        Map<String, String> params;
        com.treeage.treeagepro.oi.Report report;
        com.treeage.treeagepro.oi.Report expVal;
        com.treeage.treeagepro.oi.Graph ceGraph;
        java.lang.String nodePath;
        String vals = null;
        try {

            System.out.println("--- Running roll back and getting EV for each strategy ---");

            // Turn rollback on
            tree.setCalculationMethod(calcMet, payoff, payoff);
            params = Collections.singletonMap("enable", "true");
            report = tree.runAnalysis(AnalysisType.rollback, params, null);
            System.out.println(report.getMessage());
            System.out.println(tree.getCalculationMethod());            
            // Get expected values at the root node.
            myNode = tree.getRoot();
            expVal = myNode.expVal();
            //stampo il valore atteso al nodo radice (strategia vincente)
            System.out.println("Val at root node: " + expVal.getValue("expectedValue"));
            // Cycle through strategy nodes and collect expected values for each strategy
            for (int i = 1; i <= myNode.getBranchesNumber(); ++i) {
                nodePath = "$" + i;
                stratNode = myNode.getRelativeNode(nodePath);
                expVal = stratNode.expVal();
                System.out.println("Val at node '" + stratNode.getLabel() + "': " + expVal.getValue("expectedValue"));
                vals = vals + "|" +expVal.getValue("expectedValue");
            }

            // Turn rollback off
            params = Collections.singletonMap("enable", "false");
            report = tree.runAnalysis(AnalysisType.rollback, params, null);


        } catch (Exception e) {
            System.out.println("Error in analyzeTree. Regular exception, not RemoteException \n"
                    + e.getMessage());
        }
        return vals;
    }
    
    public ArrayList<Node> getAllBranches() throws RemoteException{
        Node node;
        node = tree.getRoot();
        ArrayList<Node> branches = new ArrayList<Node>();
        int n_branches = node.getBranchesNumber();
        for(int i=0 ; i<n_branches;i++){
            Node i_branch= node.getBranchByIndex(i);
          
            branches.add(i_branch);
            System.out.println("branch "+i+" "+i_branch.getLabel()+" "+i_branch.getNameId()+" type"+i_branch.getType());
        }
        return branches;
    }

    public String getPayoffs() {
        String payoffs = null;
        try {
            Variable myVar = tree.getVariable("payoffs"); 
            VariableDefinition myVarDef = myVar.getRootDefinition();
            payoffs = myVarDef.getValue().concat(";").concat(myVar.getComment());  
        } catch (RemoteException ex) {
            Logger.getLogger(UseObjectInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payoffs;
    }
    
    public String getStrategies(){
        String strat = null;
        try {
            Node myNode = tree.getRoot();
            for (int i = 1; i <= myNode.getBranchesNumber(); ++i) {
                String nodePath = "$" + i;
                Node stratNode = myNode.getRelativeNode(nodePath);
                strat = strat +"|" +stratNode.getLabel();
            }
        } catch (RemoteException ex) {
                Logger.getLogger(UseObjectInterface.class.getName()).log(Level.SEVERE, null, ex);
       }
        return strat;
    }
    
    public String selectNode(String nodeName){
        com.treeage.treeagepro.oi.Node node1;
        Map<String, String> params;
        com.treeage.treeagepro.oi.Report report;
        com.treeage.treeagepro.oi.Report expVal;
        com.treeage.treeagepro.oi.Graph ceGraph;
        java.lang.String nodePath;
        String val = null;
        try {
            //metodo che dato il nodo di interesse stampa il valore atteso a quel nodo
            // Turn rollback on
//            params = Collections.singletonMap("enable", "true");
//            report = tree.runAnalysis(AnalysisType.rollback, params, null);
 
            //exp values al nodo scelto con l'id
            System.out.println("\n");
            node1 = tree.getNodeById(nodeName);
            expVal = node1.expVal();
            val = expVal.getValue("expectedValue").toString();
            System.out.println("Val at root node: "+ node1.getLabel()+" " + expVal.getValue("expectedValue"));
        } catch (RemoteException ex) {
            Logger.getLogger(UseObjectInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return val;
        
    }
    
}
