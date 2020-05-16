/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.*;
import controller.*;

/**
 *
 * @author huulo
 */
public class main {
    public static void main(String[] args) throws ClassNotFoundException {
        account account = new account();
        account.setUser("huuloc123");
        account.setPassword("locpro123");
        account.setFull_name("chung huu loc");
        account.setEmail("huuloc939@gmail.com");
        account.setTeam_name("backend");
        
        account_controller accountController = new account_controller();
        
        accountController.newAccount(account);
    }
}
