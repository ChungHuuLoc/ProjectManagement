/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.*;
import db.db;

/**
 *
 * @author huulo
 */
public class account_controller {
    Connection con;
    String url,dbname;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean checkUserNameExisted(String userName) throws SQLException {
        ps = con.prepareStatement("SELECT * FROM accounts");
        rs = ps.executeQuery();
        while(rs.next()) {
            if (userName.equals(rs.getString(5))){
                return true;
            }
        }
        return false;
    }
    
    public void newAccount(account account) throws ClassNotFoundException {
        try{
           Connection conn = db.ConnectSQLServer();
           ps = conn.prepareStatement("INSERT INTO accounts (username,password,full_name,email,team_name,updated_at) VALUES (?,?,?,?,?,GETDATE())");
           ps.setString(1, account.getUser());
           ps.setString(2, account.getPassword());
           ps.setString(3, account.getFull_name());
           ps.setString(4, account.getEmail());
           ps.setString(5, account.getTeam_name());
           ps.executeUpdate();
           ps = conn.prepareStatement("INSERT INTO user_infos (account_id,full_name,email,updated_at) VALUES (?,?,?,GETDATE())");
           ps.setInt(1, account.getId());
           ps.setString(2, account.getFull_name());
           ps.setString(3, account.getEmail());
           ps.executeUpdate();
            
           JOptionPane.showMessageDialog(null, "Sign up successfully");
           
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
