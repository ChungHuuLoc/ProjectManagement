/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.project_account_controller.projectAccount;
import db.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.project;

/**
 *
 * @author huulo
 */

public class project_controller {
    Connection con;
    String url,dbname;
    PreparedStatement ps;
    ResultSet rs;
    
    public void New(project project, int accountId) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("INSERT INTO projects (name,code,start_date,end_date,fee,hours) VALUES (?,?,?,?,?,?)");
        ps.setString(1, project.getName());
        ps.setString(2, project.getCode());
        ps.setDate(3, project.getStart_date());
        ps.setDate(4, project.getEnd_date());
        ps.setDouble(5, project.getFee());
        ps.setDouble(6, project.getHours());
        ps.executeUpdate();

        conn.close();
        ps.close();

        projectAccount(getProjectJustCreated(project.getName()).getId(), accountId);
        role_controller.newRole(getProjectJustCreated(project.getName()).getId(), accountId);



        JOptionPane.showMessageDialog(null, "Create new project successfully");
    }
    
    public static project getProjectJustCreated(String projectName) throws ClassNotFoundException, SQLException {
        int id = 0;
        double fee = 0;
        Connection conn = db.ConnectSQLServer();
        PreparedStatement ps;
        ResultSet rs;
        ps = conn.prepareStatement("SELECT id,fee FROM projects WHERE name = ? AND deleted_at is NULL");
        ps.setString(1, projectName);
        rs = ps.executeQuery();
        while(rs.next()) {
            id = rs.getInt("id");
            fee = rs.getDouble("fee");
        }
        conn.close();
        ps.close();        
        rs.close();
        
        project project = new project();
        project.setId(id);
        project.setFee(fee);
        return project;
    }
    
    public void assignProject(int accountId, int projectId) throws ClassNotFoundException, SQLException {
        projectAccount(projectId, accountId);
        role_controller.newRoleUser(projectId, accountId);
    }
    
    public void Edit(project project) throws SQLException, ClassNotFoundException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("UPDATE projects SET name=?,code=?,start_date=?,end_date=?,hours=?,updated_at=? WHERE id = ? AND deleted_at is NULL");
        ps.setString(1, project.getName());
        ps.setString(2, project.getCode());
        ps.setDate(3, project.getStart_date());
        ps.setDate(4, project.getEnd_date());
        ps.setDouble(5, project.getHours());
        ps.setInt(7, project.getId());
        ps.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now().toString()));
        ps.executeUpdate();    
        
        conn.close();
        ps.close();        
    }
    
    public project getProjectById(int projectId) throws ClassNotFoundException, SQLException{
        Connection conn = db.ConnectSQLServer();
        project project = new project();
        ps = conn.prepareStatement("SELECT * FROM projects WHERE id = ? AND deleted_at is NULL");
        ps.setInt(1, project.getId());
        rs = ps.executeQuery();
        while(rs.next()) {
            project.setName(rs.getString("name"));
            project.setCode(rs.getString("code"));
            project.setStart_date(rs.getDate("start_date"));
            project.setEnd_date(rs.getDate("end_date"));
            project.setFee(rs.getDouble("fee"));
            project.setHours(rs.getDouble("hours"));            
        }
        
        conn.close();
        ps.close();
        rs.close();
        return project;
    }
    
    public void Delete(int projectId) throws SQLException, ClassNotFoundException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("UPDATE projects SET deleted_at = ? WHERE id = ? AND deleted_at is NULL");
        ps.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now().toString()));
        ps.setInt(2, projectId);
        ps.executeUpdate();
        
        conn.close();
        ps.close();        
        
        JOptionPane.showMessageDialog(null, "Delete project successfully");
    }
    
    public Vector List(int accountId) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("SELECT * FROM projects "
                + "INNER JOIN account_project on projects.id = account_project.project_id "
                + "WHERE account_project.account_id = ? AND projects.deleted_at is NULL ");
        ps.setInt(1, accountId);
        rs = ps.executeQuery();
//        Vector title = new Vector();
//        title.add("Name");
//        title.add("Code");
//        title.add("Start date");
//        title.add("End date");
//        title.add("Fee");
//        title.add("Hours");
        Vector data = new Vector();
        while(rs.next()){
            Vector project = new Vector();
            project.add(rs.getString("name"));
            project.add(rs.getString("code"));
            project.add(rs.getString("start_date"));
            project.add(rs.getString("end_date"));
            project.add(rs.getString("fee"));
            project.add(rs.getString("hours"));
            data.add(project);
        }
        conn.close();
        rs.close();
        ps.close();
        
        return data;
    }
    
    public static void updateFee(String projectName, double amount) throws SQLException, ClassNotFoundException {
        Connection conn = db.ConnectSQLServer();
        PreparedStatement ps;
        ps = conn.prepareStatement("UPDATE projects SET fee=? WHERE name = ? AND deleted_at IS NULL");
        ps.setDouble(1, amount+getProjectJustCreated(projectName).getFee());
        ps.setString(2, projectName);
        ps.executeUpdate();
        conn.close();
        ps.close();        
    }
}
