/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import View.OnSiteBookingPage;
import View.OnSiteTestingVerificationPage;
import Controller.FacilityFacade;

/**
 *
 * @author sooyewlim
 */
public class OnSiteTestingVerification {
    public static void main(String[] args) throws Exception {
        
        FacilityFacade accessFacilityController= new FacilityFacade();
        OnSiteTestingVerificationPage theView=accessFacilityController.getOnSiteTestingVerificationPage();
        theView.setVisible(true);
        
        
    }
}
