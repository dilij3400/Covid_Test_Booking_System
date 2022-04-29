/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package TestingSite;

import Booking.OnSiteBooking;
import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */

//this is an observer interface class for offShoreTestingSite 
public interface Observer {
   
    public void update(String id, String name,String phoneNumber,String suburbName,String typeOfFacility,Boolean isOperating ,Boolean allowOnSiteBooking,Boolean allowOnSiteTesting,String waitingTime,ArrayList<OnSiteBooking> booking);
        
    
}
