/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestingSite;

import Application.NewJFrame;
import Booking.CareTaker;
import Booking.Memento;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import Booking.OnSiteBooking;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
//this class is a "database" for homeBooking in this class , we apply lazy singleton design principle so that we make sure only one instance will be created through the whole life cycle 
public class TestingSiteDataSourceCollection {

    //private final String myApiKey = NewJFrame.apiKey;
    private static final String myApiKey = "nMTd7jFGPtbhJ6gpkMtRGHRQfwbj86";
    private final String rootUrl = "https://fit3077.com/api/v2";
    private ArrayList<OffShoreTestingSiteDataSource> offShoreTestingDataSource;
    private CareTaker careTaker;

    private static TestingSiteDataSourceCollection instance;

    private TestingSiteDataSourceCollection() {
        this.careTaker=CareTaker.getInstance();
        this.offShoreTestingDataSource = new ArrayList<OffShoreTestingSiteDataSource>();

    }

    public CareTaker getCareTaker() {
        return careTaker;
    }
    
    

    //lazy singleton is applied
    public static TestingSiteDataSourceCollection getInstance() throws Exception {

        if (instance == null) {

            instance = new TestingSiteDataSourceCollection();
            instance.setOffShoreTestingDataSource();

        }
        return instance;
    }

    public ArrayList<OffShoreTestingSiteDataSource> getOffShoreTestingDataSource() {
        return offShoreTestingDataSource;
    }

    //this function is to sync the data from the web service with our local database, this function will only be called automamically when a new testingSite is added however as the assignment spec mentioned nothing about adding a new testing site, so adding a new testing site is not implemented 
    public void setOffShoreTestingDataSource() throws Exception {
        offShoreTestingDataSource = new ArrayList<OffShoreTestingSiteDataSource>();
        String usersUrl = rootUrl + "/testing-site";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        ObjectNode[] jsonNodesTestingSite = mapper.readValue(response.body(), ObjectNode[].class);
        usersUrl = rootUrl + "/booking";
        client = HttpClient.newHttpClient();
        request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode[] jsonNodesBooking = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        for (ObjectNode node : jsonNodesTestingSite) {
            OffShoreTestingSiteDataSource offShoreTestingSiteDataSource = new OffShoreTestingSiteDataSource();

            offShoreTestingSiteDataSource.updateEverything(node.get("id").toString().replaceAll("^\"|\"$", ""), node.get("name").toString().replaceAll("^\"|\"$", ""), node.get("phoneNumber").toString().replaceAll("^\"|\"$", ""), node.get("address").get("suburb").toString().replaceAll("^\"|\"$", ""), node.get("additionalInfo").get("typeOfFacility").toString().replaceAll("^\"|\"$", ""), Boolean.parseBoolean(node.get("additionalInfo").get("isOperating").toString()), Boolean.parseBoolean(node.get("additionalInfo").get("allowOnSiteBooking").toString()), Boolean.parseBoolean(node.get("additionalInfo").get("allowOnSiteTesting").toString()));

            offShoreTestingDataSource.add(offShoreTestingSiteDataSource);
        }

        for (ObjectNode node : jsonNodesBooking) {
            String bookingDate = "2022-03-03"; // to bypass other bookings that don't have this additional information added
            String bookingTime = "2:00pm"; // to bypass other bookings that don't have this additional information added
            String bookingId = node.get("id").toString().replaceAll("^\"|\"$", "");
            String bookingPin = node.get("smsPin").toString().replaceAll("^\"|\"$", "");
            String bookingStatus = node.get("status").toString().replaceAll("^\"|\"$", "");
            String userId = node.get("customer").get("id").toString().replaceAll("^\"|\"$", "");
            String facilityId = node.get("testingSite").get("id").toString().replaceAll("^\"|\"$", "");
            OffShoreTestingSiteDataSource testingSite = this.searchId(facilityId);
            if (node.get("additionalInfo").toString().equals("{}") == false) {
                if (node.get("additionalInfo").has("bookingDate")) {
                    bookingDate = node.get("additionalInfo").get("bookingDate").toString().replaceAll("^\"|\"$", "");
                }

                if (node.get("additionalInfo").has("bookingTime")) {
                    bookingTime = node.get("additionalInfo").get("bookingDate").toString().replaceAll("^\"|\"$", "");
                }
                if (node.get("additionalInfo").has("bookings")) {
                    System.out.println(node.get("additionalInfo").toString());
                    for (int i = 0; i < node.get("additionalInfo").get("bookings").size(); i += 1) {
                        
                        String previousBookingDate = node.get("additionalInfo").get("bookings").get(i).get("bookingDate").toString().replaceAll("^\"|\"$", "");
                        String previousBookingTime = node.get("additionalInfo").get("bookings").get(i).get("bookingTime").toString().replaceAll("^\"|\"$", "");
                        String previousFacilityId = node.get("additionalInfo").get("bookings").get(i).get("facilityId").toString().replaceAll("^\"|\"$", "");
                        String previousModifyDate = node.get("additionalInfo").get("bookings").get(i).get("modifyDate").toString().replaceAll("^\"|\"$", "");
                        Memento memento=new Memento(previousBookingDate,previousBookingTime,previousFacilityId,previousModifyDate,bookingId);
                        this.careTaker.initializePreviousState(memento);
                    }
                }
            }
            
            OnSiteBooking newBooking = new OnSiteBooking(userId, bookingId, bookingDate, bookingTime, facilityId);
            newBooking.setPin(bookingPin);
            newBooking.setStatus(bookingStatus);
            testingSite.updateBooking(newBooking);

        }
    }

    //this function is to get a offShoreTestingSiteDataSource (observable) by providing a facility
    public OffShoreTestingSiteDataSource searchId(String facilityId) {
        for (OffShoreTestingSiteDataSource node : offShoreTestingDataSource) {
            if (node.getId().equals(facilityId)) {
                return node;
            }
        }
        return null;
    }

}
