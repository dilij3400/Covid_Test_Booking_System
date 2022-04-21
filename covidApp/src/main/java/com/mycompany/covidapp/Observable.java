/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.covidapp;

/**
 *
 * @author sooyewlim
 */
public interface Observable {
    
    public void subscribe(Observer observer);
    public void unsubscribe(Observer observer);
    public void notifyObs();
    
}
