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
    private Date bookingDate;
    private Date modifyBookingDateTime;
    private String facilityId;
    private String bookingTime;
    private String bookingId;

    public Memento(Date bookingDate, Date modifyBookingDateTime, String facilityId, String bookingTime,String bookingId) {
        this.bookingDate = bookingDate;
        this.modifyBookingDateTime = modifyBookingDateTime;
        this.facilityId = facilityId;
        this.bookingTime = bookingTime;
        this.bookingId=bookingId;
    }

    

    public Date getBookingDate() {
        return bookingDate;
    }

    public Date getModifyBookingDateTime() {
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
    
}
