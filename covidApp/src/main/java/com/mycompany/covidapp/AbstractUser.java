/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

/**
 *
 * @author ASUS
 */
public class AbstractUser {
    
    private String userID;
    private String userName;
    private String givenName;
    private String familyName;
    private String phoneNumber;
    
    public UserIdentity identity;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getIdentity(){
        
        return identity.setIdentity();
        
    }
    
    public void setNewIdentity(UserIdentity newIdentity){
        
        identity = newIdentity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "AbstractUser{" + "userID=" + userID + ", userName=" + userName + ", givenName=" + givenName + ", familyName=" + familyName + ", phoneNumber=" + phoneNumber + ", identity=" + identity + '}';
    }
    
    

    
    
    
}
