/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import Application.OnSiteBookingPage;
import Application.SearchTestingSiteView;
import TestingSite.FacilityFacade;

/**
 *
 * @author sooyewlim
 */
public class OnSiteBooking {
    public static void main(String[] args) throws Exception {
        FacilityFacade accessFacilityController= new FacilityFacade();
        OnSiteBookingPage theView=accessFacilityController.getTheBookView();
        theView.setVisible(true);
        
        
    }
    
}
