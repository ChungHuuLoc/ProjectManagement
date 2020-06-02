/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.*;
import controller.*;
import helper.*;
import redis.clients.jedis.Jedis;
import redis.redis;

/**
 *
 * @author huulo
 */
public class main {
    public static void main(String[] args) throws ClassNotFoundException, Exception {
        account account = new account();
        account.setPassword("locpro123");
        account.setFull_name("chung huu loc");
        account.setEmail("huuloc939@gmail.com");
        account.setTeam_name("backend");
        
      
        account_controller accountController = new account_controller();
        accountController.New(account);


//          accountController.signin("huuloc1", "locpro1233");

//          helper helper = new helper();
//          
//          String body = "<h1>Your reset password code is: <h1>";
//          
//          helper.send_Email(helper.getSmtpServer(), "cuquay67@gmail.com", helper.getSendFrom(), helper.getPass(), helper.getSubject(), body);

//          Jedis session = redis.Session();
//          
//          String acc = "huuloc939@gmail.com";
//          
//          session.set("userId", acc);
//          
//          
//          System.out.println("redis successfully: ============>  " + session.get("userId"));
         project project = new project();
        
         project.setName("abc");
         project.setCode("ABC");
         project.setStart_date(java.sql.Date.valueOf("2020-05-01"));
         project.setEnd_date(java.sql.Date.valueOf("2020-05-31"));
         project.setFee(1000);
         project.setHours(1600);
         
         project_controller projectController = new project_controller();
         
         projectController.New(project, 1);

    }
}
