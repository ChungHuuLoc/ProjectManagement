/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.*;
import controller.*;
import helper.*;

/**
 *
 * @author huulo
 */
public class main {
    public static void main(String[] args) throws ClassNotFoundException, Exception {
//        account account = new account();
//        account.setUser("huuloc3");
//        account.setPassword("locpro123");
//        account.setFull_name("chung huu loc");
//        account.setEmail("huuloc939@gmail.com");
//        account.setTeam_name("backend");
//        
//        account_controller accountController = new account_controller();
//        
//        accountController.New(account);

//          account_controller accountController = new account_controller();
//          accountController.signin("huuloc1", "locpro1233");

          helper helper = new helper();
          
          String body = "<h1>Your reset password code is: <h1>";
          
          helper.send_Email(helper.getSmtpServer(), "huuloc939@gmail.com", helper.getSendFrom(), helper.getPass(), helper.getSubject(), body);
    }
}
