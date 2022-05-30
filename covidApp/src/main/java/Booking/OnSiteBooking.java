/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Booking;

import Booking.Booking;
import SupportingClass.TestType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
public class OnSiteBooking implements Booking{
    private String pin;
    private String status="INITIATED";
    private String patientId;
    private TestType testType;
    private String bookingId;
    private CovidTest covidTest;
    private Date bookingDate;
    private String bookingTime;
    private Date modifyBookingDateTime=new Date();
    private String facilityId;
    private static final String myApiKey = "nMTd7jFGPtbhJ6gpkMtRGHRQfwbj86";
    
    
    public OnSiteBooking(String patientId, String bookingId, String bookingDate, String bookingTime,String facilityId){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        this.patientId = patientId;
        this.bookingId = bookingId;
        try {
            this.bookingDate = sdf.parse(bookingDate);
        } catch (ParseException ex) {
            Logger.getLogger(OnSiteBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.bookingTime = bookingTime;
        this.facilityId=facilityId;
        
      
    }

    
    
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getId() {
        return bookingId;
    }
    
    public TestType getTestType() {
        return testType;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.bookingDate = sdf.parse(bookingDate);
        } catch (ParseException ex) {
            Logger.getLogger(OnSiteBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getPatientId() {
        return patientId;
    }
    
    //this function will create a covid test instance by providing the testType (RAT/PCR)
    public void setTestType(TestType testType) throws IOException, InterruptedException{
        this.testType = testType;
        covidTest=new CovidTest(testType,patientId,bookingId);
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setModifyBookingDateTime() {
        // Input
        Date date = new Date(System.currentTimeMillis());
        this.modifyBookingDateTime = date;
    }
    public Memento storeInMemento(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new Memento(sdf.format(bookingDate),bookingTime, facilityId,sdf.format(modifyBookingDateTime),bookingId) ;
    }
    public void restoreFromMemento(Memento memento) throws IOException, InterruptedException, ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        bookingDate=sdf.parse(memento.getBookingDate());
        modifyBookingDateTime=new Date();
        facilityId=memento.getFacilityId();
        bookingTime=memento.getBookingTime();
        String bookingUrl = "https://fit3077.com/api/v2" + "/booking/" + bookingId;

            String jsonString = "{"
                    + "\"testingSiteId\":\"" + facilityId + "\","
                    + "\"additionalInfo\":" + "{\"bookingDate\":\"" + bookingDate + "\", \"bookingTime\":\"" + bookingTime + "\"" + "}" + "}";

            //send a https request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(bookingUrl))
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
    }
    public String toString() {
        return "Booking ID: "+bookingId+" Facility Id: "+facilityId+" Booking Date: "+bookingDate+" Modify Booking Date: "+modifyBookingDateTime;
    }

    
}
