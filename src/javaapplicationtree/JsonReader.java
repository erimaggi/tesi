/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationtree;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void printElement(String element){
        //JsonNode elNode = rootNode.path(element);
        System.out.println(rootNode.toString());
        System.out.println("Element: "+ rootNode.get(element).asText());
        
    }
    
    
    
}
