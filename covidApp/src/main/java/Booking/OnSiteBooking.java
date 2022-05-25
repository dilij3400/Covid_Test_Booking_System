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
    private Date modifyBookingDateTime;
    private String facilityId;
    
    
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

    public void setModifyBookingDateTime(Date modifyBookingDateTime) {
        this.modifyBookingDateTime = modifyBookingDateTime;
    }
    public Memento storeInMemento(){
        return new Memento(bookingDate,modifyBookingDateTime,facilityId,bookingTime) ;
    }
    public void restoreFromMemento(Memento memento){
        bookingDate=memento.getBookingDate();
        modifyBookingDateTime=memento.getModifyBookingDateTime();
        facilityId=memento.getFacilityId();
        bookingTime=memento.getBookingTime();
        
    }

}
