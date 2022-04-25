/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.covidapp;

/**
 *
 * @author ASUS
 */
public interface UserIdentity {
    
    String setIdentity();
    
}

class CustomerIdentity implements UserIdentity{
    
    @Override
    public String setIdentity(){
        return "Customer";
    }
}

class AdminIdentity implements UserIdentity{
    
    @Override
    public String setIdentity(){
        return "Admin";
    }
}

class ReceptionistIdentity implements UserIdentity{
    
    @Override
    public String setIdentity(){
        return "Receptionist";
    }
}

