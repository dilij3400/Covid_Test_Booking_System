/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import com.mycompany.covidapp.Booking;
import java.util.ArrayList;



/**
 *
 * @author sooyewlim
 */
public class OffShoreTestingSiteDataSource implements Observable{
    
    private String id;
    private String name;
    private String phoneNumber;
    private String suburbName;
    private String typeOfFacility;
    private ArrayList<Observer> observers;
    private boolean isOperating ;
    private boolean allowOnSiteBooking;
    private boolean allowOnSiteTesting;
    private ArrayList<Booking> booking;
    private String waitingTime;
    
    public OffShoreTestingSiteDataSource(){
        this.observers=new ArrayList<Observer>();
        this.booking=new ArrayList<Booking>();
    }
    @Override
    public void subscribe(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        int observerIndex=observers.indexOf(observer);
        observers.remove(observerIndex);
        
    }

    @Override
    public void notifyObs() {
       
        for(Observer observer:observers){
            System.out.println(observer);
            observer.update(this.id, this.name, this.phoneNumber, this.suburbName, this.typeOfFacility, this.isOperating, this.allowOnSiteBooking, this.allowOnSiteTesting,this.waitingTime,this.booking);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSuburbName() {
        return suburbName;
    }

    public String getTypeOfFacility() {
        return typeOfFacility;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public Boolean getIsOperating() {
        return isOperating;
    }

    public Boolean getAllowOnSiteBooking() {
        return allowOnSiteBooking;
    }

    public Boolean getAllowOnSiteTesting() {
        return allowOnSiteTesting;
    }

    public ArrayList<Booking> getBooking() {
        return booking;
    }
    
    public void updateWaitingTime(){
        this.waitingTime=booking.size()*15+"";
        
    }
    
    public void updateEverything(String id, String name,String phoneNumber,String suburbName,String typeOfFacility,Boolean isOperating ,Boolean allowOnSiteBooking,Boolean allowOnSiteTesting){
        this.id=id;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.suburbName=suburbName;
        this.typeOfFacility=typeOfFacility;
        this.isOperating=isOperating;
        this.allowOnSiteBooking=allowOnSiteBooking;
        this.allowOnSiteTesting=allowOnSiteTesting;
        System.out.println("hi");
        notifyObs();
    }
    
    public void updateOperating(Boolean isOperating){
        this.isOperating=isOperating;
        notifyObs();
    }
    public void updateAllowOnSiteBooking(Boolean allowOnSiteBooking){
        this.allowOnSiteBooking=allowOnSiteBooking;
        notifyObs();
    }
    public void updateAllowOnSiteTesting(Boolean allowOnSiteTesting){
        this.allowOnSiteTesting=allowOnSiteTesting;
        notifyObs();
    }
    public void updateBooking(Booking booking){
        this.booking.add(booking);
        updateWaitingTime();
        notifyObs();
    }
}
