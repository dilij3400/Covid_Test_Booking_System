/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import View.AdminBookingPanel;

import Controller.FacilityFacade;
import Model.OffShoreTestingSiteCollection;

/**
 *
 * @author sooyewlim
 */
public class AdminAllBookingPanel {
    public static void main() throws Exception {
        FacilityFacade accessFacilityController= new FacilityFacade();
        AdminBookingPanel theView=accessFacilityController.getTheAdminBookingPanelView();
        OffShoreTestingSiteCollection.getInstance();
        theView.setVisible(true);
        
        
    }
}
