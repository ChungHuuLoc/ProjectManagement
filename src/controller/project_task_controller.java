/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author huulo
 */
public class project_task_controller {
        public static void taskProject(int taskId, int projectId) throws ClassNotFoundException, SQLException {
        Connection conn = db.ConnectSQLServer(); 
            PreparedStatement ps = conn.prepareStatement("INSERT INTO task_project (task_id, project_id) VALUES (?,?)");
        ps.setInt(1, taskId);
        ps.setInt(2, projectId);
        ps.executeUpdate();
        
        conn.close();
        ps.close();           
        
    }
}
