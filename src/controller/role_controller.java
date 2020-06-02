/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author huulo
 */
public class role_controller {
       
    public static void newRole(int projectId, int accountId) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        PreparedStatement ps;
        ps = conn.prepareStatement("INSERT INTO roles (account_id, project_id, name) VALUES (?,?,?)");
        ps.setInt(1, accountId);
        ps.setInt(2, projectId); 
        ps.setString(3, "admin");
        ps.executeUpdate();
        
        conn.close();
        ps.close();        
    }

    public static void newRoleUser(int projectId, int accountId) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        PreparedStatement ps;
        ps = conn.prepareStatement("INSERT INTO roles (account_id, project_id, name) VALUES (?,?,?)");
        ps.setInt(1, accountId);
        ps.setInt(2, projectId); 
        ps.setString(3, "user");
        ps.executeUpdate();
        
        conn.close();
        ps.close();        
    }
    
    public static String CheckRole(int projectId, int accountId) throws SQLException, ClassNotFoundException {
        Connection conn = db.ConnectSQLServer();
        PreparedStatement ps;
        String role = null;
        ps = conn.prepareStatement("SELECT * FROM roles WHERE project_id = ? AND accountId = ? ");
        ps.setInt(1, projectId);
        ps.setInt(2, accountId);      
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            role = rs.getString("name");
        }
        conn.close();
        ps.close();   
        rs.close();
        return role;
    }
}
