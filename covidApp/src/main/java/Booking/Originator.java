/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

/**
 *
 * @author sooyewlim
 */
public class Originator {
    private OnSiteBooking onSiteBooking;
    public void set(OnSiteBooking newBookingState){
        onSiteBooking=newBookingState;
    }
    public Memento storeInMemento(){
        return new Memento(onSiteBooking);
    }
    public OnSiteBooking restoreFromMemento(Memento memento){
        onSiteBooking=memento.geSavedBooking();
        return onSiteBooking;
    }
}
