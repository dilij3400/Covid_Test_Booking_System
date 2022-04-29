/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import Login.LoginPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mycompany.covidapp.TokenStatus;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class stores the jwtToken from response body.
 * It has methods to verify the authenticity of the token and also to decode it.
 * @author user
 */
public class JwtToken {
    
    ObjectNode token;
    private static final String myApiKey = "zwH7TgdPHhnFrcKQtWbzqnfMMM9MKr";
    private static final String rootUrl = "https://fit3077.com/api/v1";
    String usersUrl = rootUrl + "/user";
    String jwtString;

    public JwtToken(ObjectNode token) {
        this.token = token;
    }
    
    /**
     * This method calls the POST request to verify if the jwt token is authentic or not
     * @return enumeration result to determine necessary display output.
     */
    public TokenStatus verifyJwt (){
        
        TokenStatus result = TokenStatus.NULL;
        
        String jsonString = "{\"jwt\":\"" + token.get("jwt").textValue() + "\"}";
        
        // Performing POST request for jwt token verfication
        String usersLoginUrl = usersUrl + "/verify-token";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(usersLoginUrl + "?jwt=true")) 
            .setHeader("Authorization", myApiKey)
            .header("Content-Type","application/json") 
            .POST(HttpRequest.BodyPublishers.ofString(jsonString))
            .build();
        
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                   
            // Handling forged or tampered JWT token
            if(response.statusCode() == 403 ){
                result = TokenStatus.INVALID;
//                result = "Unauthorized user, invalid or expired token";
                  
            }
            else if (response.statusCode() == 200){
               result = TokenStatus.VALID;
            }
        } 
        catch (Exception e){
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    /**
     * This method decodes the jwtToken and retrieves its payload.
     * @return A JSON Object
     * @throws Exception 
     */
    public ObjectNode jwtDecoder() throws Exception{
        
        
        String[] chunks = jwtString.split("\\.");
        
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        
        ObjectNode decodedJwtNode = new ObjectMapper().readValue(payload, ObjectNode.class);
        
       return decodedJwtNode;
    }
    
    /**
     * Sets the jwt token body for direct use in other methods.
     */
    public void setJwtString(){
        
        this.jwtString = this.token.get("jwt").textValue();
    }
    
}
