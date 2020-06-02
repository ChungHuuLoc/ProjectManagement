/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.db;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.info;

/**
 *
 * @author huulo
 */
public class info_controller {
    Connection con;
    String url,dbname;
    PreparedStatement ps;
    ResultSet rs;
    
    public info show(String email) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        info userInfo = new info();
        ps = conn.prepareStatement("SELECT * FROM user_infos WHERE email = ? AND deleted_at is NULL");
        ps.setString(1, email);
        rs = ps.executeQuery();
        while(rs.next()) {
            userInfo.setId(rs.getInt("id"));
            userInfo.setEmail(rs.getString("email"));
            userInfo.setFull_name(rs.getString("full_name"));
            userInfo.setGender(rs.getInt("gender"));
            userInfo.setAddress(rs.getString("address"));
            userInfo.setAge(rs.getInt("age"));
        }
        conn.close();
        rs.close();
        ps.close();
        return userInfo;
    }
    
    public info edit(info userInfo) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("UPDATE user_infos SET full_name = ?, gender = ?, age = ?, address = ? WHERE id = ? AND deleted_at is NULL");
        ps.setString(1, userInfo.getFull_name());
        ps.setInt(1, userInfo.getGender());
        ps.setInt(1, userInfo.getAge());
        ps.setString(1, userInfo.getAddress());
        ps.setInt(1, userInfo.getId());
        ps.executeUpdate(); 
        conn.close();
        ps.close();
        
        return show(userInfo.getEmail());
    }
}
