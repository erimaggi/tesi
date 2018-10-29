/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbConn;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author erikamaggi
 */
public class LoaderDB {
    
    String driverName = "com.mysql.jdbc.Driver";
    String databaseURL = "jdbc:mysql://localhost:3306/uceweb_aime2017?";
    String credenziali = "user=root&password=root";
    static private Connection connection;
    static private Statement statement;

    public LoaderDB() throws SQLException {
        

        try{
            Class driverClass = Class.forName(driverName);
            Driver driver = (Driver) driverClass.newInstance();
            connection = DriverManager.getConnection(databaseURL + credenziali);
            statement = connection.createStatement();
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
            System.out.println("exception!!!");
            e.getStackTrace();
        }

    }
    
    public ResultSet getRS(String query){
        ResultSet rs = null;
        try{
            rs = statement.executeQuery(query);
        }catch(SQLException e){
            e.getStackTrace();
        }
        return rs;
    }
    
}
    