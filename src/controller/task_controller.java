/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.project_task_controller.taskProject;
import db.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.project;
import model.task;

/**
 *
 * @author huulo
 */
public class task_controller {
    Connection con;
    String url,dbname;
    PreparedStatement ps;
    ResultSet rs;
    
    public void New(task task,project project, int accountId) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();  
        ps = conn.prepareStatement("INSERT INTO tasks (account_id,name,billable,rate) VALUES (?,?,?,?)");
        ps.setInt(1, accountId);
        ps.setString(2, task.getName());
        ps.setInt(3, task.getBillable());
        ps.setDouble(4, task.getRate());
        ps.executeUpdate();
        
        conn.close();
        ps.close();

        taskProject(getTaskJustCreated(task.getName()), project.getId());
 
        JOptionPane.showMessageDialog(null, "Create new task successfully");
    }
    
    
    public int getTaskJustCreated(String name) throws ClassNotFoundException, SQLException {
        int id = 0;
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("SELECT id FROM tasks WHERE name = ? AND deleted_at is NULL");
        ps.setString(1, name);
        rs = ps.executeQuery();
        while(rs.next()) {
            id = rs.getInt("id");
        }
        conn.close();
        ps.close();        
        rs.close();
        return id;           
    }
    
    public void Edit(task task) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("UPDATE tasks SET name=?,billable=?,rate=?,updated_at=? WHERE id = ? AND deleted_at is NULL");
        ps.setString(1, task.getName());
        ps.setInt(2, task.getBillable());
        ps.setDouble(3, task.getRate());
        ps.setInt(5, task.getId());
        ps.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now().toString()));
        ps.executeUpdate();    
        
        conn.close();
        ps.close();       
    }
    
    public void Delete(int id) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("UPDATE tasks SET deleted_at = ? WHERE id = ? AND deleted_at is NULL");
        ps.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now().toString()));
        ps.setInt(2, id);
        ps.executeUpdate();
        
        conn.close();
        ps.close();        
        
        JOptionPane.showMessageDialog(null, "Delete project successfully");
    }
    
    public Vector List(int projectId) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer();
        ps = conn.prepareStatement("SELECT * FROM tasks "
                + "INNER JOIN task_project ON task_project.task_id = tasks.id "
                + "WHERE task_project.project_id = ? AND tasks.deleted_at IS NULL");
        ps.setInt(1, projectId);
        rs = ps.executeQuery();
        Vector data = new Vector();
        while(rs.next()){
            Vector task = new Vector();
            task.add(rs.getString("name"));
            task.add(rs.getString("billable"));
            task.add(rs.getString("rate"));
            data.add(task);
        }
        conn.close();
        rs.close();
        ps.close();

        return data;
    }
    
    
}
