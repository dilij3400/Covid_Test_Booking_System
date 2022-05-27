/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import Application.AdminBookingPanel;

import TestingSite.FacilityFacade;
import TestingSite.OffShoreTestingSiteCollection;

/**
 *
 * @author sooyewlim
 */
public class AdminAllBookingPanel {
    public static void main(String[] args) throws Exception {
        FacilityFacade accessFacilityController= new FacilityFacade();
        AdminBookingPanel theView=accessFacilityController.getTheAdminBookingPanelView();
        OffShoreTestingSiteCollection.getInstance();
        theView.setVisible(true);
        
        
    }
}
