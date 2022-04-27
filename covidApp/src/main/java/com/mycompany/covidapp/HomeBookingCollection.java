/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
public class HomeBookingCollection {
    private static final String myApiKey = "zwH7TgdPHhnFrcKQtWbzqnfMMM9MKr";
    private static final String rootUrl = "https://fit3077.com/api/v1";
    private ArrayList<HomeBooking> homeBooking;
    private static HomeBookingCollection instance;
    private static int qrCode;

    public HomeBookingCollection() {
        this.homeBooking = new ArrayList<HomeBooking>();
        qrCode=01200;
    }
    public static HomeBookingCollection getInstance() throws IOException, InterruptedException{
        if(instance ==null){
            instance=new HomeBookingCollection();
            instance.getHomeBookingFromWebService();
            
            
        }
        return instance;
    }
    public String verifyQrCode(String qrCode){
        System.out.println(qrCode);
        System.out.println(homeBooking.size());
        for(HomeBooking node:homeBooking){
            System.out.println(node.getQrCode().toString());
            System.out.println(qrCode);
            if(node.getQrCode().toString().equals(qrCode)){
                try {
                    node.setStatus(HomeBookingRATStatus.WITHRAT);
                } catch (IOException ex) {
                    Logger.getLogger(HomeBookingCollection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HomeBookingCollection.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "QrCode is Valid, you can proceed to get your textkit";
            }
        }
        return "qr code is invalid";
    }
    
    public String addHomeBooking(String customerId,HomeBookingRATStatus bookingStatus) throws IOException, InterruptedException{
        String bookingUrl = rootUrl + "/booking";
        String qrCode=this.generateQrCode();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        
        // Input
        Date date = new Date(System.currentTimeMillis());

        // Conversion
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("ACT"));
        String text = sdf.format(date);
        
        String jsonString = "{" +
                "\"customerId\":\"" + customerId + "\","+ 
                "\"startTime\":\"" + text + "\"," +
                "\"notes\":\"" + "none" + "\"," + 
                "\"additionalInfo\":" + "{\"qrCode\":"+"\""+qrCode+"\",\"ratStatus\":"+"\""+bookingStatus+"\""+"}" + "}";
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("hi home booking collection");
        System.out.println(response.body());
        return qrCode;
        
    }
    public String generateQrCode(){
        this.qrCode+=1;
        return this.qrCode+"";
    }
    
    public void getHomeBookingFromWebService() throws IOException, InterruptedException{
        String bookingUrl = rootUrl + "/booking";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
            .newBuilder(URI.create(bookingUrl))
            .setHeader("Authorization", myApiKey)
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        ObjectNode[] jsonNodesBooking = mapper.readValue(response.body(), ObjectNode[].class);
        for (ObjectNode node: jsonNodesBooking){
            System.out.println(node.get("testingSite").toString().equals("null"));
            System.out.println(node);
            if (node.get("testingSite").toString().equals("null")){
                System.out.println(node.get("customer").get("id").toString());
                
                String customerId=node.get("customer").get("id").toString().replaceAll("^\"|\"$", "");
                String bookingId=node.get("id").toString().replaceAll("^\"|\"$", "");
                HomeBookingRATStatus bookingStatus=HomeBookingRATStatus.valueOf(node.get("additionalInfo").get("ratStatus").toString().replaceAll("^\"|\"$", ""));
                String qrCode=node.get("additionalInfo").get("qrCode").toString().replaceAll("^\"|\"$", "");
                homeBooking.add(new HomeBooking(customerId,bookingId,bookingStatus,qrCode));
            }
        }
        
        
    }
    
}
