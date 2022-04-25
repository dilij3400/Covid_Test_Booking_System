/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.covidapp;

import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */
public class Booking  {
    private String id;
    private TestType testType;
    //private ArrayList<Observer> observers;
    public Booking(String id){
        this.id=id;
    }
    
    public void setTestType(TestType testType){
        this.testType=testType;
    }

    public String getId() {
        return id;
    }

    public TestType getTestType() {
        return testType;
    }

    

}
