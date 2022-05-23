/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latihan_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author angga
 */
public class Koneksi {
    
    public Connection connect(){
        Connection conn = null;
        // Cek Driver
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e){
            System.err.println("Driver gagal!\n->"+e);
        }
        
        // Tes koneksi
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/kuliah","root","");
        } catch(SQLException e){
            System.out.println("Koneksi gagal!\n->"+e);
        }
        
        return conn;   
    }
}