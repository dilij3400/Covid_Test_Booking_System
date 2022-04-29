/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import Booking.Booking;
import SupportingClass.HomeBookingRatStatus;
import SupportingClass.TestType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author sooyewlim
 */

//this is a child class of Booking 
public class HomeBooking implements Booking{
    private String qrCode;
    private HomeBookingRatStatus status;
    private static final String myApiKey = "zwH7TgdPHhnFrcKQtWbzqnfMMM9MKr";
    private static final String rootUrl = "https://fit3077.com/api/v1";
    private String videoUrl;
    private String patientId;
    private TestType testType;
    private String bookingId;
    private CovidTest covidTest;
    
    public HomeBooking(String patientId,  String id, HomeBookingRatStatus status,String qrCode,String videoUrl){
        
        this.patientId = patientId;
        this.bookingId = id;
        this.qrCode=qrCode;
        this.status=status;
        this.videoUrl=videoUrl;
        
    }

    public String getQrCode() {
        return qrCode;
    }

    public HomeBookingRatStatus getStatus() {
        return status;
    }

    //when the status of the booking changed, it will send a patch request to changed the data in the web service as well
    public void setStatus(HomeBookingRatStatus status) throws IOException, InterruptedException {
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
    public String getId() {
        return bookingId;
    }
    
    public TestType getTestType() {
        return testType;
    }
    
    //this function will create a covid test instance by providing the testType (RAT/PCR)
    public void setTestType(TestType testType) throws IOException, InterruptedException{
        this.testType = testType;
        covidTest=new CovidTest(testType,patientId,bookingId);
    }
    
    
}
