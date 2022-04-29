/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import com.mycompany.covidapp.OnSiteBooking;
import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */
public class OffShoreTestingSite implements Observer{
    private String id;
    private String name;
    private String phoneNumber;
    private String suburbName;
    private String typeOfFacility;
    //private ArrayList<Observer> observers;
    private boolean isOperating ;
    private boolean allowOnSiteBooking;
    private boolean allowOnSiteTesting;
    private ArrayList<OnSiteBooking> booking;
    private String waitingTime;
    private Observable testingSiteDataSource;
    
    public OffShoreTestingSite(Observable testingSiteDataSource){
        this.testingSiteDataSource=testingSiteDataSource;
        testingSiteDataSource.subscribe(this);
        
        
    }

    public boolean isIsOperating() {
        return isOperating;
    }

    public boolean isAllowOnSiteBooking() {
        return allowOnSiteBooking;
    }

    public boolean isAllowOnSiteTesting() {
        return allowOnSiteTesting;
    }

    public String getWaitingTime() {
        return waitingTime;
    }
    
    
    @Override
    public void update(String id, String name, String phoneNumber, String suburbName, String typeOfFacility, Boolean isOperating, Boolean allowOnSiteBooking, Boolean allowOnSiteTesting,String waitingTime,ArrayList<OnSiteBooking> booking) {
        //this.observers=new ArrayList<Observer>();
        this.id=id;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.suburbName=suburbName;
        this.typeOfFacility=typeOfFacility;
        this.isOperating=isOperating;
        this.allowOnSiteBooking=allowOnSiteBooking;
        this.allowOnSiteTesting=allowOnSiteTesting;
        this.waitingTime=waitingTime;
        this.booking=booking;
    }

    public String getSuburbName() {
        return suburbName;
    }

    public String getTypeOfFacility() {
        return typeOfFacility;
    }

    public String getId() {
        return id;
    }
    
    public OnSiteBooking searchBooking(String id){
        for (OnSiteBooking node:booking){
            if(node.getId().equals(id)){
                return node;               
            }         
        }
        return null;
    }
    
    public OnSiteBooking searchBookingPin(String pinCode){
        OnSiteBooking bookingNode=null;
        for (OnSiteBooking node:booking){
            int bookingPin = Integer.parseInt(node.getPin());
            int inputPin = Integer.parseInt(pinCode);
            if(bookingPin == inputPin){
                
                return node;               
            }
            
            }
        return bookingNode;
        
    }

    public ArrayList<OnSiteBooking> getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return " OffShoreTestingSite{" + "id=" + id + ", name = " + name + ", phoneNumber = " + phoneNumber + ", suburbName = " + suburbName + ", typeOfFacility = " + typeOfFacility +"On Site Booking : "+isAllowOnSiteBooking()+waitingTime +" Minutes "+"Open Now : "+isIsOperating()+" On Site Testing : "+ isAllowOnSiteTesting()+ '}';
    }
    
        
}
