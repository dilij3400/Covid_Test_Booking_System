/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import Controller.FacilityFacade;
import View.BookingModificationPage;

/**
 *
 * @author ASUS
 */
public class OnSiteBookingModify {
    public static void main(String[] args) throws Exception {
        FacilityFacade accessFacilityController= new FacilityFacade();
        BookingModificationPage theView = accessFacilityController.getBookingModificationPage();
        theView.setVisible(true);
        
        
    }
    
}
