/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

/**
 *
 * @author ASUS
 */
public class HealthCareWorker extends AbstractUser {
    
    private static HealthCareWorker instance;

    public HealthCareWorker() {
        
        super();
        
        identity = new AdminIdentity();
    }
    
    public static HealthCareWorker getInstance(){
        if(instance == null){
            instance = new HealthCareWorker();
        }
        
        return instance;
    }
}
