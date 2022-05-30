/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import View.VerifyQrCodePage;
import Controller.BookingFacade;

/**
 *
 * @author sooyewlim
 */
public class VerifyQrCode {
    public static void main(String[] args) throws Exception {
        
        
        BookingFacade accessBookingController= new BookingFacade();
        VerifyQrCodePage theView=accessBookingController.getVerifyQrCodePage();
        theView.setVisible(true);
        
        
    }
}
