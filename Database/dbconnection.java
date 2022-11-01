/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author KARONEI
 */
public class dbconnection {
    
        public static Connection pharm_db(){
        
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy","root","");
           //JOptionPane.showMessageDialog(null, "Succefull Connected");
           return conn;
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, "Not connection start Your server start your sever first");
             System.exit(0);
        }
        return null;
        
    }
}
