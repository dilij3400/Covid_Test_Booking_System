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
import java.util.ArrayList;

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
    private String bookingDate;
    private String bookingTime;
    public OnSiteBooking(String patientId, String bookingId, String bookingDate, String bookingTime){
        
        this.patientId = patientId;
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        
      
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
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
    
    
    

    
    

    
    
    


    

    
    

}
