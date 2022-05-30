/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import Application.OnSiteTesting;
import Booking.BookingFacade;

/**
 *
 * @author sooyewlim
 */
public class OnSiteTestResult {
    public static void main(String[] args) throws Exception {
        BookingFacade accessBookingController=new BookingFacade();
        OnSiteTesting theView=accessBookingController.getOnSiteTesting();
        theView.setVisible(true);
        
        
    }
}
