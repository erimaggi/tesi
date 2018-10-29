/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationtree;

import com.treeage.treeagepro.oi.AnalysisType;
import com.treeage.treeagepro.oi.Distribution;
import com.treeage.treeagepro.oi.Node;
import com.treeage.treeagepro.oi.Tree;
import com.treeage.treeagepro.oi.TreeAgeProApplication;
import com.treeage.treeagepro.oi.Variable;
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
        //apertura dell'ultimo albero aperto su treeAge
        try {
            //tree = app.getTopTree();
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
    
    public void copyTree() {
        //salvataggio con un nuovo nome
        //String newName = "prometeo/home/cl416968/Desktop/proveTreeage/newTree1.trex";
        String newName = "C:/Users/cl416968/treeage/workspace/newTree.trex";
        try {
            tree.saveAs(newName);
            //Tree tree = app.getTree("prova1Luglio2008.trex"); //non funziona
            if (tree.isValid()) {
                System.out.println("The currently saved tree is: " + tree.getTreeName());
                tree.close();
                openTree(newName);
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
            //Tree tree = app.getTree("prova1Luglio2008.trex"); //non funziona
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
    
    public void updateVariable(String varToChange) {

        com.treeage.treeagepro.oi.Variable myVar;
        com.treeage.treeagepro.oi.VariableDefinition myVarDef;

        try {

            System.out.println("--- Updating variable props and definition ---");

            myVar = tree.getVariable(varToChange);
            System.out.println("Variable name current: " + myVar.getName());
            System.out.println("Variable desc current: " + myVar.getDescription());
            myVar.setDescription("Costo del test");
            System.out.println("Variable desc new:     " + myVar.getDescription());

            tree.getRoot();
            myVarDef = myVar.getRootDefinition();
            System.out.println("Variable def at root current: " + myVarDef.getValue());
            myVarDef.setValue("50");
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
    public void analyzeTree() throws RemoteException{

        com.treeage.treeagepro.oi.Node myNode;
        com.treeage.treeagepro.oi.Node stratNode;
        Map<String, String> params;
        com.treeage.treeagepro.oi.Report report;
        com.treeage.treeagepro.oi.Report expVal;
        com.treeage.treeagepro.oi.Graph ceGraph;
        java.lang.String nodePath;

        try {

            System.out.println("--- Running roll back and getting EV for each strategy ---");

            // Turn rollback on
            params = Collections.singletonMap("enable", "true");
            report = tree.runAnalysis(AnalysisType.rollback, params, null);
            
            // Get expected values at the root node.
            myNode = tree.getRoot();
            expVal = myNode.expVal();
            if (tree.getCalculationMethod().equals("ct_costEff")) {
                System.out.println("Val at root node: ");
                System.out.println("  Cost: " + expVal.getValue("cost"));
                System.out.println("  Eff:  " + expVal.getValue("effectiveness"));
            } else {
                System.out.println("Val at root node: " + expVal.getValue("expectedValue"));
            }

            // Cycle through strategy nodes and collect expected values for each strategy
            for (int i = 1; i <= myNode.getBranchesNumber(); ++i) {

                nodePath = "$" + i;
                stratNode = myNode.getRelativeNode(nodePath);
                expVal = stratNode.expVal();
                if (tree.getCalculationMethod().equals("ct_costEff")) {
                    System.out.println("Val at node '" + stratNode.getLabel() + "': ");
                    System.out.println("  Cost: " + expVal.getValue("cost"));
                    System.out.println("  Eff:  " + expVal.getValue("effectiveness"));
                } else {
                    System.out.println("Val at node '" + stratNode.getLabel() + "': " + expVal.getValue("expectedValue"));
                }

            }

            // Turn rollback off
            params = Collections.singletonMap("enable", "false");
            report = tree.runAnalysis(AnalysisType.rollback, params, null);

            // Run cost-effectiveness analysis on CE tree
            if (tree.getCalculationMethod().equals("ct_costEff")) {
                System.out.println("--- Running Cost-Effectiveness analysis ---");
                report = tree.runAnalysis(AnalysisType.costEffectivenes, null, myNode);
                System.out.println("CE text report:");
                //outputTextReport(report.getTextReport());
                if (report.getGraph() != null) {
                    System.out.println("Saving CEA graph image.");
                    ceGraph = report.getGraph();
                    String graphFile = app.getWorkspacePath() + "/Example Models/Healthcare/CE-graph.png";
                    ceGraph.saveAsImage(graphFile);
                    System.out.println("CEA graph image file is " + graphFile);
                }
            }

            System.out.println("--- Running Monte Carlo simulation ---");

            // Setup simulation parameters, several just use default
            // Run PSA on this specific CE tree, trials on the sample tree
            params = new HashMap<String, String>();
            if (tree.getCalculationMethod().equals("ct_costEff")) {
                params.put("samples", "100");
                params.put("trials", "0");
            } else {
                params.put("samples", "0");
                params.put("trials", "100");
            }

            report = tree.runAnalysis(AnalysisType.monteCarlo, params, myNode);

            System.out.println("Simulation complete. Calc time: " + report.getCalcTime() + ", Status: " + report.getStatus());



            // Getting secondary charts
            if (!tree.getCalculationMethod().equals("ct_costEff")) {
                // this particular example gets charts of CE analysis only
                return;
            }

        System.out.println("Java OI example completed succesfully!");
        } catch (RemoteException re) {
            System.out.println("Error in analyzeTree.");
            throw re;
        } catch (Exception e) {
            System.out.println("Error in analyzeTree. Regular exception, not RemoteException \n"
                    + e.getMessage());
            return;
        }

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
    
}
