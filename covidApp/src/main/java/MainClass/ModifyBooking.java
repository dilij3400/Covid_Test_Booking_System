/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import Application.BookingModificationPage;
import TestingSite.FacilityFacade;
import TestingSite.OffShoreTestingSiteCollection;

/**
 *
 * @author sooyewlim
 */
public class ModifyBooking {
    public static void main(String[] args) throws Exception {
        FacilityFacade accessFacilityController= new FacilityFacade();
        BookingModificationPage theView=accessFacilityController.getTheModifyBookingView();
        OffShoreTestingSiteCollection.getInstance();
        theView.setVisible(true);
        
        
    }
}