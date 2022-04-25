/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

/**
 *
 * @author ASUS
 */
public class Administrator extends AbstractUser {
    
    private static Administrator instance;

    public Administrator() {
        
        super();
        
        identity = new AdminIdentity();
    }
    
    public static Administrator getInstance(){
        if(instance == null){
            instance = new Administrator();
        }
        
        return instance;
    }
}
