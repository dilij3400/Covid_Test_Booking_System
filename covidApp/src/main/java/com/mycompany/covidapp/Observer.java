/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.covidapp;

import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */
public interface Observer {
   
    public void update(String id, String name,String phoneNumber,String suburbName,String typeOfFacility,Boolean isOperating ,Boolean allowOnSiteBooking,Boolean allowOnSiteTesting,String waitingTime,ArrayList<Booking> booking);
        
    
}
