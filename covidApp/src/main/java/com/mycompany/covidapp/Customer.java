/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

/**
 *
 * @author ASUS
 */
public class Customer extends AbstractUser{
    
    private static Customer instance;
    
    public Customer() {
        
        super();
        
        // sets user type to customer.
        identity = new CustomerIdentity();
    }
    
    public static Customer getInstance(){
        if(instance == null){
            instance = new Customer();
        }
        
        return instance;
    }
    
    
}
