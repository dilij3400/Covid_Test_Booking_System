/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestingSite;

import Application.NewJFrame;
import Application.OnSiteBookingPage;
import Application.OnSiteTesting;
import Application.OnSiteTestingVerificationPage;
import Application.OnlineOnSiteTestingBooking;
import Application.SearchTestingSiteView;
import Booking.OnSiteBooking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
public class FacilityFacade {
    private SearchTestingSiteView theSearchView;
    private OnSiteBookingPage theBookView;
    private OnSiteTestingVerificationPage theVerifyView;
    private OnlineOnSiteTestingBooking theOnlineBookingView;
    //private static final String myApiKey = NewJFrame.apiKey;
    private static final String myApiKey="nMTd7jFGPtbhJ6gpkMtRGHRQfwbj86";
    private static final String rootUrl = "https://fit3077.com/api/v2";
    OffShoreTestingSiteCollection offShoreTestingSiteCollection;
    TestingSiteDataSourceCollection testingSiteDataSourceCollection;

    public FacilityFacade() throws Exception {
        this.offShoreTestingSiteCollection = OffShoreTestingSiteCollection.getInstance();
        this.testingSiteDataSourceCollection = TestingSiteDataSourceCollection.getInstance();
        this.theSearchView=new SearchTestingSiteView();
        this.theBookView=new OnSiteBookingPage();
        this.theOnlineBookingView=new OnlineOnSiteTestingBooking();
        this.theVerifyView=new OnSiteTestingVerificationPage();
        this.theSearchView.addSearchListener(new SearchListener());
        this.theBookView.addBookListener(new OnSiteBookingListener());
        this.theVerifyView.addVerifyListener(new OnSiteVerifyListener());
        this.theOnlineBookingView.addBookListener(new OnlineOnSiteBookingListener());
        
        
    }
    
    class SearchListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            String suburbName=theSearchView.getSuburbName();
            String facilityType=theSearchView.getFacilityType();
            ArrayList<OffShoreTestingSite> searchTestingSiteResult=searchTestingSite(suburbName,facilityType);
            theSearchView.updateView(searchTestingSiteResult);
        }
    }
    
    class OnlineOnSiteBookingListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            String facilityId=theOnlineBookingView.getFacilityId();
            String customerId=theOnlineBookingView.getCustomerId();
            String bookingResult=bookOnSiteBooking(facilityId,customerId);
            theOnlineBookingView.updateView(bookingResult);
        }
    }
    
    class OnSiteBookingListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            String facilityId=theBookView.getFacilityId();
            String customerId=theBookView.getCustomerId();
            String bookingResult=bookOnSiteBooking(facilityId,customerId);
            theBookView.updateView(bookingResult);
        }
    }
    class OnSiteVerifyListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            String facilityId=theVerifyView.getFacilityId();
            String bookingId=theVerifyView.getBookingId();
            OnSiteBooking onSiteBooking=verifyOnSiteBooking(facilityId,bookingId);
            String verifyResult;
            if (onSiteBooking!=null){
                verifyResult="This is a valid booking";
                OnSiteTesting obj=new OnSiteTesting();
                obj.setBooking(onSiteBooking);
                obj.setVisible(true);
                
            }
            else{
                verifyResult="This is not a valid bookingid ";
            }
            theVerifyView.updateView(verifyResult);
        }
    }

    public SearchTestingSiteView getTheSearchView() {
        return theSearchView;
    }

    public OnSiteBookingPage getTheBookView() {
        return theBookView;
    }
    public OnSiteTestingVerificationPage getOnSiteTestingVerificationPage(){
        return theVerifyView;
    }
    
    //this method is to add the on site book by providing faility id and customer id and it will be pushed to the web service
    public String bookOnSiteBooking(String facilityId,String customerId){
        String bookingResult="";
        String usersUrl = rootUrl + "/user";
        HttpResponse response;
        
        // searching for a specific facility given facility id.
        OffShoreTestingSite testingSite = offShoreTestingSiteCollection.searchId(facilityId);
        // Check if facility exists
        if (testingSite == null){
            bookingResult="Facility does not exist";
        }
        else if (testingSite!=null){
            
                // perform GET request of all customers.
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest
                        .newBuilder(URI.create(usersUrl))
                        .setHeader("Authorization", myApiKey)
                        .GET()
                        .build();
            
                try{
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                
                    ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body().toString(), ObjectNode[].class);
                    
                    // Iterate through each customer and cross check if customer id is equal to input id.
                    for (ObjectNode node: jsonNodes) {
                        String id = node.get("id").toString();
                        String result = id.replaceAll("^\"|\"$", "");
                        if(!result.equals(customerId)){
                        
                            bookingResult="Customer does not exist";
                        }
                        else{
                            
                            //testingSiteDataSourceCollection = TestingSiteDataSourceCollection.getInstance();
                            OffShoreTestingSiteDataSource offShoreTestingSiteDataSource = testingSiteDataSourceCollection.searchId(facilityId);
                        
                            // Make booking
                            OnSiteBooking onSiteBooking=new OnSiteBooking(customerId,facilityId);
                            
                            response = offShoreTestingSiteDataSource.addBooking(onSiteBooking,customerId,facilityId);
                        
                        
                            if(response.statusCode() == 201){
                                ObjectNode jsonNode = new ObjectMapper().readValue(response.body().toString(), ObjectNode.class);
                                bookingResult="Booking created successfully, your PIN number is : " + jsonNode.get("smsPin");
                                onSiteBooking.setPin(jsonNode.get("smsPin").toString().replaceAll("^\"|\"$", ""));
                                System.out.println(onSiteBooking.getPin());
                            }
                            else if (response.statusCode() == 404){
                                bookingResult="A customer and/or testing site with the provided ID was not found.";
                            }
                            else{
                                bookingResult="Error";
                            }
                            break;
                        }
                    }
                }
                catch (Exception e){
                    Logger.getLogger(FacilityFacade.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        return bookingResult;
    }
    
    //done
    public ArrayList<OffShoreTestingSite> searchTestingSite(String suburb,String facility){
        ArrayList<OffShoreTestingSite> searchTestingSiteResult=OffShoreTestingSiteCollection.getInstance().search(suburb, facility);
        return searchTestingSiteResult;
    }
    
    public OnSiteBooking searchOnSiteBooking(String bookingId){
        OnSiteBooking booking=offShoreTestingSiteCollection.searchBooking(bookingId);
        return booking;
    }
    
    public OnSiteBooking verifyOnSiteBooking(String facilityId,String bookingId){
        OffShoreTestingSite testingSite=offShoreTestingSiteCollection.searchId(facilityId);
        OnSiteBooking currentUserBooking=null;
        if (testingSite!=null){
            currentUserBooking=testingSite.searchBooking(bookingId);
        }
        return currentUserBooking;
    }
}
