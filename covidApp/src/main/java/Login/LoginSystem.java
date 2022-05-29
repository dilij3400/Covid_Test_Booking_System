/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import SupportingClass.TokenStatus;
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
 * @author ASUS
 */
public class LoginSystem {
    private String username;
    private String password;
    private static final String myApiKey = "7jQBGHHKgGwpmhdFwJPKzgQDqmtBg8";
    private static final String rootUrl = "https://fit3077.com/api/v2";
    String usersUrl = rootUrl + "/user";
     
    ObjectNode jsonNodeJWT;
    ObjectNode userNode;

    public LoginSystem(String username, String password) {
        this.username = username;
        this.password = password;
    }  
    
    
    public String verifyLogin(){
        
        String output = "Success!";
        
        String jsonString = "{" +
      "\"userName\":\"" + username + "\"," +
      "\"password\":\"" + password + "\"" +
      "}";
        
        String usersLoginUrl = usersUrl + "/login";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(usersLoginUrl + "?jwt=true")) 
            .setHeader("Authorization", myApiKey)
            .header("Content-Type","application/json") 
            .POST(HttpRequest.BodyPublishers.ofString(jsonString))
            .build();
        
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                   
            // Handling incorrect password or username
            if(response.statusCode() == 403 ){
                output = "Incorrect username or password";
            }
            else if (response.statusCode() == 200){
                // Validating jwtToken and decoding it.
                jsonNodeJWT = new ObjectMapper().readValue(response.body(), ObjectNode.class);
                JwtToken token = new JwtToken(jsonNodeJWT);
                token.setJwtString();
                TokenStatus jwtVerifyResult = token.verifyJwt();
                
                if(jwtVerifyResult == TokenStatus.VALID){
                    userNode = token.jwtDecoder();                       
                    
                    AbstractUser user = setUser(userNode);
                    user.display();
                }
                else if(jwtVerifyResult == TokenStatus.INVALID){
                   output = "Unauthorized user, invalid or expired token";
                }
            }
        } 
        catch (Exception e){
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, e);
        }
        return output;
    }
    
    public AbstractUser setUser(ObjectNode userNodeInput){
        
        // Creating user based on user role
        if (userNode.get("isCustomer").asBoolean()){
            AbstractUser customer = new Customer();

            customer.setUserID(userNode.get("sub").textValue());
            customer.setUserName(userNode.get("username").textValue());
            customer.setGivenName(userNode.get("givenName").textValue());
            customer.setFamilyName(userNode.get("familyName").textValue());
            customer.setPhoneNumber(userNode.get("phoneNumber").textValue());

            // directs user to customer dashboard
            return customer;
        }

        if(userNode.get("isHealthcareWorker").asBoolean()){
            AbstractUser healthCareWorker = new HealthCareWorker();

            healthCareWorker.setUserID(userNode.get("sub").textValue());
            healthCareWorker.setUserName(userNode.get("username").textValue());
            healthCareWorker.setGivenName(userNode.get("givenName").textValue());
            healthCareWorker.setFamilyName(userNode.get("familyName").textValue());
            healthCareWorker.setPhoneNumber(userNode.get("phoneNumber").textValue());

            // directs user to healthcare worker dashboard
            return healthCareWorker;

        }

        if(userNode.get("isReceptionist").asBoolean()){
            AbstractUser receptionist = new Receptionist();

            receptionist.setUserID(userNode.get("sub").textValue());
            receptionist.setUserName(userNode.get("username").textValue());
            receptionist.setGivenName(userNode.get("givenName").textValue());
            receptionist.setFamilyName(userNode.get("familyName").textValue());
            receptionist.setPhoneNumber(userNode.get("phoneNumber").textValue());

            // directs user to receptionist/admin dashboard
            return receptionist;

        }
        
        return null;
    }
    
}


