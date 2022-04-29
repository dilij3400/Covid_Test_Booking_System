/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

/**
 *
 * @author ASUS
 */
public class Receptionist extends AbstractUser {
    
    /**
     * Class for Receptionist
     */
    
    public Receptionist() {
        
        super();
        
        // sets user type to receptionist/admin.
        identity = new ReceptionistIdentity();
    }
    @Override
    public void display() {
        AdminOption adminPage = new AdminOption();
        adminPage.setVisible(true);
    }
    
}
