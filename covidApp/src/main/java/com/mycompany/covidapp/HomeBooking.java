/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author sooyewlim
 */
public class HomeBooking extends Booking{
    private String qrCode;
    private HomeBookingRATStatus status;
    private static final String myApiKey = "zwH7TgdPHhnFrcKQtWbzqnfMMM9MKr";
    private static final String rootUrl = "https://fit3077.com/api/v1";
    
    public HomeBooking(String patientId,  String id, HomeBookingRATStatus status,String qrCode){
        super(patientId,id);
        this.qrCode=qrCode;
        this.status=status;
        
    }

    public String getQrCode() {
        return qrCode;
    }

    public HomeBookingRATStatus getStatus() {
        return status;
    }

    public void setStatus(HomeBookingRATStatus status) throws IOException, InterruptedException {
        this.status = status;
        String bookingUrl = rootUrl + "/booking"+this.getId();
        String jsonString = "{" +"\"additionalInfo\":" + "{"+"\"ratStatus\":"+"\""+getStatus()+"\""+"}" + "}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json")
                .method("PATCH",HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    
}
