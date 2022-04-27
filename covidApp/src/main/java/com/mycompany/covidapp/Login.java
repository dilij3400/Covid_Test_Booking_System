/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.covidapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */

public class Login extends javax.swing.JFrame {
    
    private static final String myApiKey = "zwH7TgdPHhnFrcKQtWbzqnfMMM9MKr";
    private static final String rootUrl = "https://fit3077.com/api/v1";
    String usersUrl = rootUrl + "/user";
    
    private boolean result = false;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        login = new javax.swing.JButton();
        usernameText = new javax.swing.JTextField();
        passwordText = new javax.swing.JTextField();
        messageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Covid Test Registration System");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(login))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameText, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(passwordText))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 166, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(179, 179, 179))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(login)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        // TODO add your handling code here:
        
        AdminOption adminPage = new AdminOption();
        HealthCareWorkerOption healthCarePage=new HealthCareWorkerOption();
        UserOption userOption=new UserOption();
        // reset display message
        messageLabel.setText("");
        
        ObjectNode jsonNodeJWT;
        
        ObjectNode userNode;
        
        String jwt;
        
        String jsonString = "{" +
      "\"userName\":\"" + usernameText.getText().trim() + "\"," +
      "\"password\":\"" + passwordText.getText().trim() + "\"" +
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
                messageLabel.setText("Incorrect username or password");
            }
            else if (response.statusCode() == 200){
                // Storing JWT Token
                jsonNodeJWT = new ObjectMapper().readValue(response.body(), ObjectNode.class);
                if(verifyJwt(jsonNodeJWT)){
                    jwt = jsonNodeJWT.get("jwt").textValue();
                    
                    userNode = jwtDecoder(jwt);
                    
                    // Creating user based on user role
                    if (userNode.get("isCustomer").asBoolean()){
                        AbstractUser customer = Customer.getInstance();
                        
                        customer.setUserID(userNode.get("sub").textValue());
                        customer.setUserName(userNode.get("username").textValue());
                        customer.setGivenName(userNode.get("givenName").textValue());
                        customer.setFamilyName(userNode.get("familyName").textValue());
                        customer.setPhoneNumber(userNode.get("phoneNumber").textValue());
                        
                        userOption.setVisible(true);
                        
                    }
                    
                    if(userNode.get("isHealthcareWorker").asBoolean()){
                        AbstractUser healthCareWorker = HealthCareWorker.getInstance();
                        
                        healthCareWorker.setUserID(userNode.get("sub").textValue());
                        healthCareWorker.setUserName(userNode.get("username").textValue());
                        healthCareWorker.setGivenName(userNode.get("givenName").textValue());
                        healthCareWorker.setFamilyName(userNode.get("familyName").textValue());
                        healthCareWorker.setPhoneNumber(userNode.get("phoneNumber").textValue());
                        healthCarePage.setVisible(true);
                        
                    }
                    
                    if(userNode.get("isReceptionist").asBoolean()){
                        AbstractUser receptionist = Receptionist.getInstance();
                        
                        receptionist.setUserID(userNode.get("sub").textValue());
                        receptionist.setUserName(userNode.get("username").textValue());
                        receptionist.setGivenName(userNode.get("givenName").textValue());
                        receptionist.setFamilyName(userNode.get("familyName").textValue());
                        receptionist.setPhoneNumber(userNode.get("phoneNumber").textValue());
                        adminPage.setVisible(true);
                        
                    }
                }

            }
        } 
        catch (Exception e){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }//GEN-LAST:event_loginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    public boolean verifyJwt (ObjectNode jsonNode){
        
        // reset result
        result = false;
        
        String jsonString = "{\"jwt\":\"" + jsonNode.get("jwt").textValue() + "\"}";
        
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
                messageLabel.setText("Unauthorized user, invalid or expired token");
            }
            else if (response.statusCode() == 200){
               result = true;
            }
        } 
        catch (Exception e){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public ObjectNode jwtDecoder(String jwt) throws Exception{
        
        String[] chunks = jwt.split("\\.");
        
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        
        ObjectNode decodedJwtNode = new ObjectMapper().readValue(payload, ObjectNode.class);
        
       return decodedJwtNode;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton login;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField passwordText;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
