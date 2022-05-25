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
    private ArrayList<Memento> twoPreviousBooking=new ArrayList<Memento>();
    public void aaddMemento(Memento m){
        twoPreviousBooking.add(m);
    }

    public Memento getMemento(int index) {
        return twoPreviousBooking.get(index);
    }
    
}
