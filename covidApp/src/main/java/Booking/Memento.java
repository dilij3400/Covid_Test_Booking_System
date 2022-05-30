/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.util.Date;

/**
 *
 * @author sooyewlim
 */
public class Memento {
    private String bookingDate;
    private String modifyBookingDateTime;
    private String facilityId;
    private String bookingTime;
    private String bookingId;

    public Memento(String bookingDate, String bookingTime, String facilityId,String modifyBookingDateTime,String bookingId) {
        this.bookingDate = bookingDate;
        this.modifyBookingDateTime = modifyBookingDateTime;
        this.facilityId = facilityId;
        this.bookingTime = bookingTime;
        this.bookingId=bookingId;
    }

    

    public String getBookingDate() {
        return bookingDate;
    }

    public String getModifyBookingDateTime() {
        return modifyBookingDateTime;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getBookingId() {
        return bookingId;
    }
    
    public String toString() {
        return "Facility Id: "+facilityId+" Booking Date: "+bookingDate+" Modify Booking Date: "+modifyBookingDateTime;
    }
    
}
