/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import java.io.IOException;

/**
 *
 * @author sooyewlim
 */
public abstract class Booking {
    private String patientId;
    private TestType testType;
    private String bookingId;
    private CovidTest covidTest;
    

    public Booking(String patientId, String id) {
        this.patientId = patientId;
        this.bookingId = id;
        
    }
    
    public String getId() {
        return bookingId;
    }
    
    public TestType getTestType() {
        return testType;
    }
    
    public void setTestType(TestType testType) throws IOException, InterruptedException{
        this.testType = testType;
        covidTest=new CovidTest(testType,patientId,bookingId);
    }
    
    
}


