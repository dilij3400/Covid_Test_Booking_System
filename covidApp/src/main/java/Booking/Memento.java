/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

/**
 *
 * @author sooyewlim
 */
public class Memento {
    private OnSiteBooking onSiteBooking;

    public Memento(OnSiteBooking onSiteBooking) {
        this.onSiteBooking = onSiteBooking;
    }
    
    public OnSiteBooking geSavedBooking(){
        return onSiteBooking;
    }
}
