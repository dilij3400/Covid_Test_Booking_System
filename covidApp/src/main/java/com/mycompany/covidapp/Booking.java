/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.covidapp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */
public class Booking  {
    private final String myApiKey = "HN9t9JCtwNhcCP6mnWBgg6prpcKrPw";
    private final String rootUrl = "https://fit3077.com/api/v1";
    private String id;
    private String pin;
    private TestType testType;
    private String status;
    private String patientId;
    //private ArrayList<Observer> observers;
    public Booking(String id, String pin, String status,String patientId){
        this.id = id;
        this.pin = pin;
        this.status = status;
        this.patientId=patientId;
    }
    
    public void setTestType(TestType testType) throws IOException, InterruptedException{
        
    }


    public String getId() {
        return id;
    }

    public TestType getTestType() {
        return testType;
    }

    public String getPin() {
        return pin;
    }

    public String getStatus() {
        return status;
    }

    
    

}
