/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Booking.HomeBooking;
import Booking.HomeBooking;
import SupportingClass.HomeBookingRatStatus;
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

//this class is a "database" for homeBooking in this class , we apply lazy singleton design principle so that we make sure only one instance will be created through the whole life cycle 
public class HomeBookingCollection {
    private static final String myApiKey = "zwH7TgdPHhnFrcKQtWbzqnfMMM9MKr";
    private static final String rootUrl = "https://fit3077.com/api/v1";
    private ArrayList<HomeBooking> homeBooking;
    private static HomeBookingCollection instance;
    private static int qrCode;
    private final String urlBookingVideoConferencing="www.booking/videoconferencing";
    

    public HomeBookingCollection() {
        this.homeBooking = new ArrayList<HomeBooking>();
        qrCode=01200;
    }
    
    //lazy singleton is applied
    public static HomeBookingCollection getInstance() throws IOException, InterruptedException{
        if(instance ==null){
            instance=new HomeBookingCollection();
            instance.getHomeBookingFromWebService();
            
            
        }
        return instance;
    }
    
    //this function will return a text that indicate whether the provided qrCode is valid 
    public String verifyQrCode(String qrCode){
        
        for(HomeBooking node:homeBooking){
            
            if(node.getQrCode().toString().equals(qrCode)){
                try {
                    node.setStatus(HomeBookingRatStatus.WITHRAT);
                } catch (IOException ex) {
                    Logger.getLogger(HomeBookingCollection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HomeBookingCollection.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "Qr Code is Valid, you can proceed to get your testkit";
            }
        }
        return "Qr code is invalid";
    }
    
    //this function will allow us to add a new homeBooking to the system by providing customerID and bookingStatus (whether the user need a Rat or without)
    public String[] addHomeBooking(String customerId,HomeBookingRatStatus bookingStatus) throws IOException, InterruptedException{
        String bookingUrl = rootUrl + "/booking";
        String qrCode=this.generateQrCode();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        String urlVideoConferecing=urlBookingVideoConferencing+"/"+customerId;
        
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
                "\"additionalInfo\":" + "{\"qrCode\":"+"\""+qrCode+"\",\"ratStatus\":"+"\""+bookingStatus+"\",\"videoUrl\":"+"\""+urlVideoConferecing+"\""+"}" + "}";
        System.out.println(jsonString);
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
        getHomeBookingFromWebService();
        return new String[] {qrCode,urlVideoConferecing};
        
    }
    
    //generate a new qr code
    public String generateQrCode(){
        this.qrCode+=1;
        return this.qrCode+"";
    }
    
    //this function is to sync the data from the web service with our local database, this function will only be called automamically when a new booking is added 
    public void getHomeBookingFromWebService() throws IOException, InterruptedException{
        this.homeBooking = new ArrayList<HomeBooking>();
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
            
            if (node.get("testingSite").toString().equals("null")){
                
                
                String customerId=node.get("customer").get("id").toString().replaceAll("^\"|\"$", "");
                String bookingId=node.get("id").toString().replaceAll("^\"|\"$", "");
                HomeBookingRatStatus bookingStatus=HomeBookingRatStatus.valueOf(node.get("additionalInfo").get("ratStatus").toString().replaceAll("^\"|\"$", ""));
                String qrCode=node.get("additionalInfo").get("qrCode").toString().replaceAll("^\"|\"$", "");
                String videoUrl=node.get("additionalInfo").get("videoUrl").toString().replaceAll("^\"|\"$", "");
                homeBooking.add(new HomeBooking(customerId,bookingId,bookingStatus,qrCode,videoUrl));
            }
        }
        
        
    }
    
}
