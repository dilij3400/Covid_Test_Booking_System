/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */
public class CareTaker {
    private ArrayList<Memento> previousBooking=new ArrayList<Memento>();
    public void aaddMemento(Memento m){
        previousBooking.add(m);
    }

    public ArrayList<Memento> getMemento(String bookingId) {
        ArrayList<Memento> bookingMemento=new ArrayList<Memento>();
        for (Memento node:previousBooking){
            
        }
        return bookingMemento;
    }
    
}
