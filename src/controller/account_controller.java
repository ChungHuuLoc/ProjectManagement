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
import helper.helper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import view.InforForm;
//import redis.clients.jedis.Jedis;
//import redis.redis;

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
           ps = conn.prepareStatement("INSERT INTO accounts (password,full_name,email,team_name) VALUES (?,?,?,?)");
           ps.setString(2, md5(account.getPassword()));
           ps.setString(3, account.getFull_name());
           ps.setString(4, account.getEmail());
           ps.setString(5, account.getTeam_name());
           ps.executeUpdate();
      
           newInfo(getAccountId(account.getEmail()), account.getFull_name(), account.getEmail());
           
           JOptionPane.showMessageDialog(null, "Sign up successfully");
           
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public int getAccountId(String email) throws SQLException, ClassNotFoundException {
        Connection conn = db.ConnectSQLServer();
        int id = 0;
        ps = conn.prepareStatement("SELECT id FROM accounts WHERE email = ? AND deleted_at is NULL");
        ps.setString(1, email);
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
    
    public void signin(String email, String password) throws ClassNotFoundException {
        try{
            Connection conn = db.ConnectSQLServer();
            ps = conn.prepareStatement("SELECT * FROM accounts WHERE email = ? AND deleted_at is NULL");
            ps.setString(1, email);
            rs = ps.executeQuery();
            while(rs.next()) {
                if (md5(password).compareTo(rs.getString("password")) == 0) {
                    //Jedis session = redis.Session();
                    //session.set("email", rs.getString("email"));
                 
                    JOptionPane.showMessageDialog(null, "Sign in successfully");
                   // int id = rs.getInt("account_id");
                   InforForm form = new InforForm(email);
           form.setVisible(true);
                    
                  return ;
                }
            }
            
            JOptionPane.showMessageDialog(null, "Email or password is incorrect");
        }
        catch(SQLException e) {
           // JOptionPane.showMessageDialog(null, "Email or password is incorrect");
        }
     
    }
    
    public void Verify(String code, String email) throws ClassNotFoundException {
        try {
            Connection conn = db.ConnectSQLServer();
            ps = conn.prepareStatement("SELECT * FROM user_infos WHERE email = ? AND deleted_at is NULL");
            ps.setString(1, email);  
            rs = ps.executeQuery();
            while(rs.next()){
                if (rs.getString("code").equals(code)) {
                    JOptionPane.showMessageDialog(null, "Verify successfully");
                }
            }
            
            JOptionPane.showMessageDialog(null, "Invalid code");
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Invalid code");
        } 
    }
    
    public void updatePassword(String email, String password) throws ClassNotFoundException {
        try {
            Connection conn = db.ConnectSQLServer();
            ps = conn.prepareStatement("UPDATE accounts SET password = ? WHERE email = ? AND deleted_at is NULL");
            ps.setString(1, password); 
            ps.setString(2, email);
            ps.executeUpdate();            
            
            JOptionPane.showMessageDialog(null, "Password has changed");
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Some thing went wrong!");
        }         
    }
    
    public void sendCodeResetPass(String email) throws ClassNotFoundException, Exception {
        try{
            
            String randCode = generateRandomCode(email);
            String body = "<h1>Your reset password code is: "+ randCode +"+<h1>";
            
            Connection conn = db.ConnectSQLServer();
            ps = conn.prepareStatement("SELECT * FROM user_infos WHERE email = ? AND deleted_at is NULL");
            ps.setString(1, email);
            rs = ps.executeQuery();
            while(rs.next()) {
                helper.send_Email(helper.getSmtpServer(), rs.getString("email"), helper.getSendFrom(), helper.getPass(), helper.getSubject(), body);
                JOptionPane.showMessageDialog(null, "Mail has sent");
                return;
            }
            
            String body1 = "<h1>Can not find any account of this email <h1>";
            helper.send_Email(helper.getSmtpServer(), email, helper.getSendFrom(), helper.getPass(), helper.getSubject(), body1);
            JOptionPane.showMessageDialog(null, "Mail has sent");
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    
    public String generateRandomCode(String email) throws ClassNotFoundException, SQLException {
        String code = md5(email);
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("UPDATE user_infos SET code = ? WHERE email = ? AND deleted_at is NULL");
        ps.setString(1, code); 
        ps.setString(2, email);
        ps.executeUpdate();
        
        return code;
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
