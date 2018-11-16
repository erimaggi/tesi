/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author erikamaggi
 */
public class JsonReader {
    
    String fileName;
    JsonNode rootNode;

    public JsonReader(String fileName){
        this.fileName = fileName;
    }

    public JsonNode readJson(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readTree(new File(fileName));
        } catch (IOException ex) {
            
        }
        return rootNode;
    }
    
    public String getTree(String element){
        return rootNode.get(element).asText();
    }
    
    public void printElement(String element){
        System.out.println("Element: "+ rootNode.get(element).asText());
        JsonNode mappingList = rootNode.get(element);
        if (mappingList.isArray()){
            ArrayList<String> nodes = (ArrayList) mappingList.findValuesAsText("node");
            ArrayList<String> vars = (ArrayList) mappingList.findValuesAsText("varSecondTree");
            System.out.println(nodes.isEmpty());
            for (String node : nodes) {
                System.out.println(node);
            }
            for (String var : vars) {
                System.out.println(var);
            }
        }
    }
    
    public ArrayList<String> readNode(String element){
        //System.out.println("Element: "+ rootNode.get(element).asText());
        JsonNode mappingList = rootNode.get(element);
        ArrayList<String> nodes = (ArrayList) mappingList.findValuesAsText("node");
        return nodes;
    }
    
    public ArrayList<String> readVars(String element){
        //System.out.println("Element: "+ rootNode.get(element).asText());
        JsonNode mappingList = rootNode.get(element);
        ArrayList<String> vars = (ArrayList) mappingList.findValuesAsText("varSecondTree");
        return vars;
    }

    
    
    
}
