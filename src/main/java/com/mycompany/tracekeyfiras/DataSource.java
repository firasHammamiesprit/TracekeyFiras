/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tracekeyfiras;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class DataSource {
 private String url;
    private String login; 
    private String password;
    private Connection connection;
    private static DataSource datasource;

    private DataSource() {
         url ="jdbc:mysql://localhost:3306/Tracekey";
         login="root";
         password ="";
         
        try {
           connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
      
      
        
    
    }

    public Connection getConnection() {
        return connection;
    }
    
    public static DataSource getInstance(){
       if(datasource==null){
       datasource = new DataSource();
       }
        return datasource;
    }
    
}
