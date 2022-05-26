/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestingSite;

import Application.NewJFrame;
import Booking.CareTaker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import Booking.OnSiteBooking;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
//this class is an observable/subject (observer design pattern)
public class OffShoreTestingSiteDataSource implements Observable {

    //private static final String myApiKey = NewJFrame.apiKey;
    private static final String myApiKey = "nMTd7jFGPtbhJ6gpkMtRGHRQfwbj86";
    private static final String rootUrl = "https://fit3077.com/api/v2";

    private String id;
    private String name;
    private String phoneNumber;
    private String suburbName;
    private String typeOfFacility;
    private ArrayList<Observer> observers;
    private boolean isOperating;
    private boolean allowOnSiteBooking;
    private boolean allowOnSiteTesting;
    private ArrayList<OnSiteBooking> booking;
    private String waitingTime;
    private ArrayList<CareTaker> bookingCareTaker;

    public OffShoreTestingSiteDataSource() {
        this.observers = new ArrayList<Observer>();
        this.booking = new ArrayList<OnSiteBooking>();
    }

    @Override
    public void subscribe(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        int observerIndex = observers.indexOf(observer);
        observers.remove(observerIndex);

    }

    //this method will be called when the attribute of this instance is updated
    @Override
    public void notifyObs() {

        for (Observer observer : observers) {
            System.out.println("datasource update");
            System.out.println(observer);
            observer.update(this.id, this.name, this.phoneNumber, this.suburbName, this.typeOfFacility, this.isOperating, this.allowOnSiteBooking, this.allowOnSiteTesting, this.waitingTime, this.booking);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSuburbName() {
        return suburbName;
    }

    public String getTypeOfFacility() {
        return typeOfFacility;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public Boolean getIsOperating() {
        return isOperating;
    }

    public Boolean getAllowOnSiteBooking() {
        return allowOnSiteBooking;
    }

    public Boolean getAllowOnSiteTesting() {
        return allowOnSiteTesting;
    }

    public ArrayList<OnSiteBooking> getBooking() {
        return booking;
    }

    public void updateWaitingTime() {
        this.waitingTime = booking.size() * 15 + "";

    }

    public void updateEverything(String id, String name, String phoneNumber, String suburbName, String typeOfFacility, Boolean isOperating, Boolean allowOnSiteBooking, Boolean allowOnSiteTesting) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.suburbName = suburbName;
        this.typeOfFacility = typeOfFacility;
        this.isOperating = isOperating;
        this.allowOnSiteBooking = allowOnSiteBooking;
        this.allowOnSiteTesting = allowOnSiteTesting;
        System.out.println("hi");
        notifyObs();
    }

    public void updateOperating(Boolean isOperating) {
        this.isOperating = isOperating;
        notifyObs();
    }

    public void updateAllowOnSiteBooking(Boolean allowOnSiteBooking) {
        this.allowOnSiteBooking = allowOnSiteBooking;
        notifyObs();
    }

    public void updateAllowOnSiteTesting(Boolean allowOnSiteTesting) {
        this.allowOnSiteTesting = allowOnSiteTesting;
        notifyObs();
    }

    //here line 151 code the logic to send a patch request to a specific booking in its additionalinfo to store 
    //facility id , booking date , booking time 
    //this function is to add a new booking by providing patientId and string id and it will send a post request to web service 
    public HttpResponse addBooking(String customerId, String facilityId, String bookingDate, String bookingTime) throws IOException, InterruptedException {

        notifyObs();
        String bookingUrl = rootUrl + "/booking";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        // Input
        Date date = new Date(System.currentTimeMillis());

        // Conversion
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("ACT"));
        String text = sdf.format(date);

        String jsonString = "{"
                + "\"customerId\":\"" + customerId + "\","
                + "\"testingSiteId\":\"" + facilityId + "\","
                + "\"startTime\":\"" + text + "\","
                + "\"notes\":\"" + "none" + "\","
                + "\"additionalInfo\":" + "{\"bookingDate\":\"" + bookingDate + "\", \"bookingTime\":\"" + bookingTime + "\"" + "}" + "}";
        //send a https request 
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode jsonNode = new ObjectMapper().readValue(response.body().toString(), ObjectNode.class);
        String bookingId = jsonNode.get("smsPin").toString().replaceAll("^\"|\"$", "");
        OnSiteBooking onSiteBooking = new OnSiteBooking(customerId, bookingId, bookingDate, bookingTime, facilityId);
        onSiteBooking.setPin(jsonNode.get("smsPin").toString().replaceAll("^\"|\"$", ""));
        this.updateBooking(onSiteBooking);
        return response;

    }

    // This function modifies an existing booking
    public String modifyBookingDateTime(String bookingId, String bookingDate, String bookingTime) throws IOException, InterruptedException {
        String returnMessage = "";
        if (this.checkBookingDate(bookingId, bookingDate) == true) {
            String bookingUrl = rootUrl + "/booking/" + bookingId;

            String jsonString = "{"
                    + "\"additionalInfo\":" + "{\"bookingDate\":\"" + bookingDate + "\", \"bookingTime\":\"" + bookingTime + "\"" + "}" + "}";

            //send a https request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(bookingUrl))
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.modifyBookingDateTimeLocally(bookingId, bookingDate, bookingTime, true);
            returnMessage = "booking modified succuessfully";
        } else {
            returnMessage = "booking can't be modified please change the booking date or this booking might has already tested";
        }
        return returnMessage;
    }

    public String modifyBookingFacility(String bookingId, String facilityId) throws IOException, InterruptedException {
        String returnMessage = "";
        if (this.verifyBookingStatus(bookingId) == true) {
            String bookingUrl = rootUrl + "/booking/" + bookingId;

            String jsonString = "{"
                    + "\"testingSiteId\":\"" + facilityId + "\"" + "}";

            //send a https request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(bookingUrl))
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                    .build();
            //memento of this part is done
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.removeBooking(bookingId, facilityId);
            returnMessage = "booking modified succuessfully";
        } else {
            returnMessage = "booking can't be modified please change the booking date or this booking might has already tested";
        }
        return returnMessage;
    }

    public String modifyBookingDateTimeFacility(String bookingId, String facilityId, String bookingDate, String bookingTime) throws IOException, InterruptedException {
        String returnMessage = "";
        if (this.checkBookingDate(bookingId, bookingDate) == true) {
            String bookingUrl = rootUrl + "/booking/" + bookingId;

            String jsonString = "{"
                    + "\"testingSiteId\":\"" + facilityId + "\","
                    + "\"additionalInfo\":" + "{\"bookingDate\":\"" + bookingDate + "\", \"bookingTime\":\"" + bookingTime + "\"" + "}" + "}";

            //send a https request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(bookingUrl))
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.modifyBookingDateTimeLocally(bookingId, bookingDate, bookingTime, false);
            this.removeBooking(bookingId, facilityId);
            returnMessage = "booking modified succuessfully";
        }
        else {
            returnMessage = "booking can't be modified please change the booking date or this booking might has already tested";
        }
        return returnMessage;
    }

    //this function is to update local database 
    public void updateBooking(OnSiteBooking newBooking) {
        booking.add(newBooking);
        this.updateWaitingTime();
        notifyObs();

    }

    public void modifyBookingDateTimeLocally(String bookingId, String bookingDate, String bookingTime, Boolean modifyDateTimeOnly) {
        CareTaker caretaker = null;
        for (CareTaker node : this.bookingCareTaker) {
            if (node.getBookingId().equals(bookingId)) {
                caretaker = node;
            }
        }
        if (caretaker == null) {
            caretaker = new CareTaker(bookingId);
            this.bookingCareTaker.add(caretaker);
        }
        for (OnSiteBooking node : booking) {
            if (node.getId().equals(bookingId)) {
                if (modifyDateTimeOnly == true) {
                    caretaker.addMemento(node.storeInMemento());
                }
                Date date = new Date();
                node.setModifyBookingDateTime(date);
                node.setBookingDate(bookingDate);
                node.setBookingTime(bookingTime);

                break;
            }

        }

    }

    public Boolean checkBookingDate(String bookingId, String bookingDate) {
        Boolean validBooking = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date bookingDateToModify = sdf.parse(bookingDate);
            OnSiteBooking onSiteBooking = this.searchBooking(bookingId);
            Date currentDate = new Date();
            if (onSiteBooking.getStatus() != "INITIATED") {
                validBooking = false;
                return validBooking;
            }
            if (bookingDateToModify.compareTo(currentDate) < 0) {
                validBooking = false;
                return validBooking;
            }
        } catch (ParseException ex) {
            Logger.getLogger(OnSiteBooking.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validBooking;
    }

    public Boolean verifyBookingStatus(String bookingId) {
        Boolean validBooking = true;

        OnSiteBooking onSiteBooking = this.searchBooking(bookingId);

        if (onSiteBooking.getStatus() != "INITIATED") {
            validBooking = false;
            return validBooking;
        }

        return validBooking;
    }

    public OnSiteBooking removeBooking(String bookingId, String facilityId) {
        OnSiteBooking onSiteBooking = null;
        CareTaker caretaker = null;
        for (CareTaker node : this.bookingCareTaker) {
            if (node.getBookingId().equals(bookingId)) {
                caretaker = node;
            }
        }
        if (caretaker == null) {
            caretaker = new CareTaker(bookingId);
            this.bookingCareTaker.add(caretaker);
        }
        for (int i = 0; i < booking.size(); i += 1) {
            if (booking.get(i).getId().equals(bookingId)) {
                caretaker.addMemento(booking.get(i).storeInMemento());
                booking.get(i).setFacilityId(facilityId);
                Date date = new Date();
                booking.get(i).setModifyBookingDateTime(date);
                onSiteBooking = booking.get(i);
                booking.remove(i);
                break;
            }

        }
        notifyObs();
        return onSiteBooking;
    }

    public OnSiteBooking searchBooking(String bookingId) {
        OnSiteBooking onSiteBooking = null;
        for (OnSiteBooking node : booking) {
            if (node.getId().equals(bookingId)) {
                return node;
            }
        }
        return onSiteBooking;
    }

}
