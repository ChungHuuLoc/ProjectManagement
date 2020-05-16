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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author huulo
 */
public class account_controller {
    Connection con;
    String url,dbname;
    PreparedStatement ps;
    ResultSet rs;
    
    public void New(account account) throws ClassNotFoundException {
        try{
           Connection conn = db.ConnectSQLServer();
           ps = conn.prepareStatement("INSERT INTO accounts (username,password,full_name,email,team_name) VALUES (?,?,?,?,?)");
           ps.setString(1, account.getUser());
           ps.setString(2, md5(account.getPassword()));
           ps.setString(3, account.getFull_name());
           ps.setString(4, account.getEmail());
           ps.setString(5, account.getTeam_name());
           ps.executeUpdate();
      
           newInfo(getAccountId(account.getUser()), account.getFull_name(), account.getEmail());
           
           JOptionPane.showMessageDialog(null, "Sign up successfully");
           
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public int getAccountId(String username) throws SQLException, ClassNotFoundException {
        Connection conn = db.ConnectSQLServer();
        int id = 0;
        ps = conn.prepareStatement("SELECT id from accounts WHERE username = ? AND deleted_at is NULL");
        ps.setString(1, username);
        rs = ps.executeQuery();
        while(rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }
    
    public void newInfo(int id, String fullName, String email) throws ClassNotFoundException, SQLException {
           Connection conn = db.ConnectSQLServer();
           ps = conn.prepareStatement("INSERT INTO user_infos (account_id,full_name,email) VALUES (?,?,?)");
           ps.setInt(1, id);
           ps.setString(2, fullName);
           ps.setString(3, email);
           ps.executeUpdate();
    }
    
    private String md5(String msg) {
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(msg.getBytes());
        byte byteData[] = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        return "";
    }
}
}
