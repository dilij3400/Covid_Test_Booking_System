/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import SupportingClass.TestType;
import java.io.IOException;

/**
 *
 * @author sooyewlim
 */
interface Booking {

    
    public String getId();
        
    
    
    public TestType getTestType();
    
    //this function will create a covid test instance by providing the testType (RAT/PCR)
    public void setTestType(TestType testType) throws IOException, InterruptedException;
    
    
    
    
}


