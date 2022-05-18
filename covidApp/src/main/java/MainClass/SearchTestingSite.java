/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import Application.SearchTestingSiteView;
import TestingSite.FacilityFacade;

/**
 *
 * @author sooyewlim
 */
public class SearchTestingSite {
    public static void main(String[] args) throws Exception {
        
        
        FacilityFacade accessFacilityController= new FacilityFacade();
        SearchTestingSiteView theView=accessFacilityController.getTheSearchView();
        theView.setVisible(true);
        
        
    }
}
