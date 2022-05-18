/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import Application.VerifyQrCodePage;
import SupportingClass.HomeBookingRatStatus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 *
 * @author sooyewlim
 */
public class BookingFacade {
    HomeBookingCollection homeBookingCollection;
    private VerifyQrCodePage theQrVerificationView;       
    public BookingFacade() throws IOException, InterruptedException {
        this.homeBookingCollection = homeBookingCollection=HomeBookingCollection.getInstance();
        this.theQrVerificationView=new VerifyQrCodePage();
        this.theQrVerificationView.addVerifyListener(new verifyQrCodeListener());
        
    }
    class verifyQrCodeListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            String qrCode=theQrVerificationView.getQrCode();
            String result=verifyQrCode(qrCode);
            theQrVerificationView.updateView(result);
        }
    }
    
    public VerifyQrCodePage getVerifyQrCodePage(){
        return this.theQrVerificationView;
    }
    
    public String verifyQrCode(String qrCode){
        String result=homeBookingCollection.verifyQrCode(qrCode);
        return result;
    }
    
    public String [] bookHomeBooking(String customerId,HomeBookingRatStatus bookingStatus) throws IOException, InterruptedException{
        String [] qrCodeAndUrl =homeBookingCollection.addHomeBooking(customerId,bookingStatus);
        return qrCodeAndUrl;
        
    }
    
    
}
