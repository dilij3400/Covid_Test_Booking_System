/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestingSite;

import Booking.OnSiteBooking;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */

//this class is a "database" for homeBooking in this class , we apply lazy singleton design principle so that we make sure only one instance will be created through the whole life cycle 
public class OffShoreTestingSiteCollection {
    private ArrayList<OffShoreTestingSite> offShoreTesting;
    private static OffShoreTestingSiteCollection instance;
    private OffShoreTestingSiteCollection() {
        this.offShoreTesting=new ArrayList<OffShoreTestingSite>();    
        
    }
    //lazy singleton is applied
    public static OffShoreTestingSiteCollection getInstance(){
        if(instance ==null){
            instance=new OffShoreTestingSiteCollection();
            instance.setOffShoreTestingSite();
        }
        return instance;
    }
    //this function is to sync the data from the web service with our local database
    public void setOffShoreTestingSite(){
        try {
            ArrayList<OffShoreTestingSiteDataSource> offShoreTestingDataSource=TestingSiteDataSourceCollection.getInstance().getOffShoreTestingDataSource();
            for (OffShoreTestingSiteDataSource node:offShoreTestingDataSource){
                OffShoreTestingSite offShoreTestingSite=new OffShoreTestingSite(node);
                node.notifyObs();
                offShoreTesting.add(offShoreTestingSite);
                }
            } 
        catch (Exception ex) {
            Logger.getLogger(OffShoreTestingSiteCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ArrayList<OffShoreTestingSite> getOffShoreTesting() {
        return offShoreTesting;
    }
    
    
    
    public OnSiteBooking searchBooking(String bookingID){
        OnSiteBooking onSiteBooking=null;
        for (OffShoreTestingSite testingSite:offShoreTesting){
            onSiteBooking=testingSite.searchBooking(bookingID);
            if(onSiteBooking !=null){
                return onSiteBooking;
            }
        }
        return onSiteBooking;
    }
        
    // this function is mainly for task2 , provide a suburbName or typeOfFacility it will return a list of offShoreTestingSite 
    public ArrayList<OffShoreTestingSite> search(String suburbName,String typeOfFacility){
        ArrayList<OffShoreTestingSite> offShoreTestingSiteList=new ArrayList<OffShoreTestingSite>();
        for (OffShoreTestingSite node:offShoreTesting){
            System.out.println("Search function in offSHorecollection");
            System.out.println(node.getSuburbName());
            System.out.println(node.getTypeOfFacility());
            if(node.getSuburbName().equals(suburbName) || node.getTypeOfFacility().equals(typeOfFacility)){
                offShoreTestingSiteList.add(node);
            }
        }
        return offShoreTestingSiteList;
    }  
    
    //this function will a offShoreTestingSite instance by providing facility id 
    public OffShoreTestingSite searchId(String facilityId) {
        for (OffShoreTestingSite node:offShoreTesting){
            if(node.getId().equals(facilityId)){
                return node;
            }
        }
        return null;
    }

    
     
}
