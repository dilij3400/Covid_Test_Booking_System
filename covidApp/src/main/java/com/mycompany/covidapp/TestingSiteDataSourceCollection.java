/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mycompany.covidapp.Booking;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 *
 * @author sooyewlim
 */
public class TestingSiteDataSourceCollection {
    private final String myApiKey = "HN9t9JCtwNhcCP6mnWBgg6prpcKrPw";
    private final String rootUrl = "https://fit3077.com/api/v1";
    private ArrayList<OffShoreTestingSiteDataSource> offShoreTestingDataSource;
    
    private static TestingSiteDataSourceCollection instance;
    
    private TestingSiteDataSourceCollection() {
        
        this.offShoreTestingDataSource=new ArrayList<OffShoreTestingSiteDataSource>();    
        
    }
    public static TestingSiteDataSourceCollection getInstance() throws Exception{
        
        if(instance ==null){
            
            instance=new TestingSiteDataSourceCollection();
            instance.setOffShoreTestingDataSource();
            
        }
        return instance;
    }

    public ArrayList<OffShoreTestingSiteDataSource> getOffShoreTestingDataSource() {
        return offShoreTestingDataSource;
    }

    public void setOffShoreTestingDataSource() throws Exception{
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
        
        for (ObjectNode node: jsonNodesTestingSite) {
            OffShoreTestingSiteDataSource offShoreTestingSiteDataSource=new OffShoreTestingSiteDataSource();
            
            offShoreTestingSiteDataSource.updateEverything(node.get("id").toString(), node.get("name").toString(),node.get("phoneNumber").toString(),node.get("address").get("suburb").toString(),node.get("additionalInfo").get("typeOfFacility").toString(),Boolean.parseBoolean(node.get("additionalInfo").get("isOperating").toString()) ,Boolean.parseBoolean(node.get("additionalInfo").get("allowOnSiteBooking").toString()),Boolean.parseBoolean(node.get("additionalInfo").get("allowOnSiteTesting").toString()));
            System.out.println("search2");
            offShoreTestingDataSource.add(offShoreTestingSiteDataSource);
        }
        
        for (ObjectNode node: jsonNodesBooking) {
            String bookingId=node.get("id").toString();
            String bookingPin = node.get("smsPin").toString();
            String bookingStatus = node.get("status").toString();
            Booking newBooking= new Booking(bookingId, bookingPin, bookingStatus);
            for (OffShoreTestingSiteDataSource testingSite:offShoreTestingDataSource ){
                if (node.get("testingSite").get("id").toString().equals(testingSite.getId())){
                    testingSite.updateBooking(newBooking);
                }
            }
            
        }
    }
    
    
    
}
