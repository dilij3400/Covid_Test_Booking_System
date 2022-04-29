/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestingSite;

import Application.NewJFrame;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
public class OnSiteBookingSubSystem {
    private static final String myApiKey = NewJFrame.apiKey;
    private static final String rootUrl = "https://fit3077.com/api/v1";
    OffShoreTestingSiteCollection offShoreTestingSiteCollection;
    TestingSiteDataSourceCollection testingSiteDataSourceCollection;

    public OnSiteBookingSubSystem() throws Exception {
        this.offShoreTestingSiteCollection = OffShoreTestingSiteCollection.getInstance();
        this.testingSiteDataSourceCollection = TestingSiteDataSourceCollection.getInstance();
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
                            
                            TestingSiteDataSourceCollection testingSiteDataSourceCollection = TestingSiteDataSourceCollection.getInstance();
                            OffShoreTestingSiteDataSource offShoreTestingSiteDataSource = testingSiteDataSourceCollection.searchId(facilityId);
                        
                            // Make booking
                        
                            response = offShoreTestingSiteDataSource.addBooking(customerId,facilityId);
                        
                        
                            if(response.statusCode() == 201){
                                ObjectNode jsonNode = new ObjectMapper().readValue(response.body().toString(), ObjectNode.class);
                                bookingResult="Booking created successfully, your PIN number is : " + jsonNode.get("smsPin");
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
                    Logger.getLogger(OnSiteBookingSubSystem.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        return bookingResult;
    }
    
    
}
