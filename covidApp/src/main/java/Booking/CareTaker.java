/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */
public class CareTaker {
    //private static final String myApiKey = NewJFrame.apiKey;
    private static final String myApiKey = "nMTd7jFGPtbhJ6gpkMtRGHRQfwbj86";
    private static final String rootUrl = "https://fit3077.com/api/v2";
    private ArrayList<Memento> previousBooking;
    private static CareTaker instance;
    
    public CareTaker(){
        this.previousBooking=new ArrayList<Memento>();
    }
    
    public static CareTaker getInstance(){
        if(instance==null){
            instance=new CareTaker();
            
        }
        return instance;
    }
    
    public void addMemento(Memento m,String currentBookingDate,String currentBookingTime){
        previousBooking.add(m);
        String bookingId=m.getBookingId();
        String bookingModifyDate=m.getModifyBookingDateTime();
        String bookingUrl = rootUrl + "/booking/" + bookingId;
        String jsonString = "{"
                    + "\"additionalInfo\":" + "{\"bookingDate\":\"" + currentBookingDate + "\", \"bookingTime\":\"" + currentBookingTime + "\",";
        String bookingJsonString = "\"bookings\":[";
        ArrayList<Memento> previousBookingOfThisBookingId=getPreviousBooking(bookingId);
        for (int i = 0; i < previousBookingOfThisBookingId.size(); i += 1) {
                
                Memento node = previousBookingOfThisBookingId.get(i);
                System.out.println(node);
                if (i != previousBookingOfThisBookingId.size() - 1) {
                    bookingJsonString += "{\"bookingDate\":\"" + node.getBookingDate() + "\", \"bookingTime\":\"" + node.getBookingTime() + "\", \"facilityId\":\"" + node.getFacilityId() + "\", \"modifyDate\":\"" + node.getModifyBookingDateTime()+"\"" + "},";
                }
                else{
                    bookingJsonString += "{\"bookingDate\":\"" + node.getBookingDate() + "\", \"bookingTime\":\"" + node.getBookingTime() + "\", \"facilityId\":\"" + node.getFacilityId() + "\", \"modifyDate\":\"" +node.getModifyBookingDateTime()+"\"" + "}";
                }
            }
            bookingJsonString +="]";
            jsonString+=bookingJsonString + "}"+ "}";
        HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(bookingUrl))
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                    .build();
            
    }
    
    public void initializePreviousState(Memento m){
        previousBooking.add(m);
    }

    

    public ArrayList<Memento> getPreviousBooking() {
        return previousBooking;
    }

    public Memento getMemento(int index) {
        
        return previousBooking.get(index);
    }

    
    
    public ArrayList<Memento> getPreviousBooking(String bookingId){
        ArrayList<Memento> previousBookingOfThisBookingId=new ArrayList<Memento>();
        for (Memento node:previousBooking){
            if(node.getBookingId().equals(bookingId)){
                previousBookingOfThisBookingId.add(node);
                if(previousBooking.size()>2){
                    previousBooking.remove(0);
                }
            }
        }
        return previousBookingOfThisBookingId;
    }
    
    
}
