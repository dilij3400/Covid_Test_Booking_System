/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
public class OffShoreTestingSiteCollection {
    private ArrayList<OffShoreTestingSite> offShoreTesting;
    private static OffShoreTestingSiteCollection instance;
    private OffShoreTestingSiteCollection() {
        this.offShoreTesting=new ArrayList<OffShoreTestingSite>();    
        
    }
    public static OffShoreTestingSiteCollection getInstance(){
        if(instance ==null){
            instance=new OffShoreTestingSiteCollection();
            instance.setOffShoreTestingSite();
        }
        return instance;
    }
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
    public OffShoreTestingSite search(String suburbName,String typeOfFacility){
        System.out.println(suburbName);
        System.out.println(typeOfFacility);
        System.out.println(offShoreTesting.size());
        for (OffShoreTestingSite node:offShoreTesting){
            System.out.println(node.getSuburbName());
            System.out.println(node.getTypeOfFacility());
            if(node.getSuburbName().equals(suburbName) && node.getTypeOfFacility().equals(typeOfFacility)){
                return node;
            }
        }
        return null;
    }  
    

    public OffShoreTestingSite searchId(String facilityId) {
        for (OffShoreTestingSite node:offShoreTesting){
            if(node.getId().equals(facilityId)){
                return node;
            }
        }
        return null;
    }

    
     
}
