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
import org.mortbay.util.ajax.JSON;

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
    //private ArrayList<Observer> observers;
    public Booking(String id){
        this.id=id;
    }
    
    public void setTestType(TestType testType) throws IOException, InterruptedException{
        String jsonString="";
        switch(testType){
            case PCR:
                jsonString="{\"additionalInfo\":" + "{\"testType\":\"PCR\"}"+"}";
                //jsonString=JSON.stringify({additionalInfo:{testType:"PCR"});
                break;
            case RAT:
                jsonString="{\"additionalInfo\":" + "{\"testType\":\"RAT\"}"+"}";
                break;
        }
        this.testType=testType;
        
        String bookingId=this.id.substring(1, this.id.length()-1);
        String bookingUrl = rootUrl + "/booking/"+bookingId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(bookingUrl))
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
            .setHeader("Authorization", myApiKey)
            .header("Content-Type","application/json")
            .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(bookingUrl);
        System.out.println(jsonString);
    }
    public void setPin(String pin) throws IOException, InterruptedException{
        this.pin=pin;
        String jsonString="{\"additionalInfo\":" + "{\"pinCode\":"+pin+"}"+"}";
        String bookingId=this.id.substring(1, this.id.length()-1);
        String bookingUrl = rootUrl + "/booking/"+bookingId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(bookingUrl))
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
            .setHeader("Authorization", myApiKey)
            .header("Content-Type","application/json")
            .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String getId() {
        return id;
    }

    public TestType getTestType() {
        return testType;
    }

    

}
