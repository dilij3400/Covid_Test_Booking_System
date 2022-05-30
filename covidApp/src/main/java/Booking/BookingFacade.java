/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import Application.OnSiteTesting;
import Application.VerifyQrCodePage;
import SupportingClass.HomeBookingRatStatus;
import SupportingClass.TestType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
public class BookingFacade {

    HomeBookingCollection homeBookingCollection;
    private VerifyQrCodePage theQrVerificationView;
    private OnSiteTesting onSiteTesting;

    public BookingFacade() throws IOException, InterruptedException {
        this.homeBookingCollection = homeBookingCollection = HomeBookingCollection.getInstance();
        this.theQrVerificationView = new VerifyQrCodePage();
        this.onSiteTesting = new OnSiteTesting();
        this.theQrVerificationView.addVerifyListener(new verifyQrCodeListener());

    }

    public OnSiteTesting getOnSiteTesting() {
        return onSiteTesting;
    }
    
    class verifyQrCodeListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String qrCode = theQrVerificationView.getQrCode();
            String result = verifyQrCode(qrCode);
            theQrVerificationView.updateView(result);
        }
    }

    class SubmitTestingResult implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            OnSiteBooking booking = onSiteTesting.getBooking();
            if (onSiteTesting.getIsCough() || onSiteTesting.getIsFever() || onSiteTesting.getIsLossTaste() || onSiteTesting.getIsHeadache()) {
                onSiteTesting.updateTestResult("He should do A PCR test");

                try {
                    booking.setTestType(TestType.PCR);
                } catch (IOException ex) {
                    Logger.getLogger(OnSiteTesting.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(OnSiteTesting.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                onSiteTesting.updateTestResult("He should do A RAT test");
                try {
                    booking.setTestType(TestType.RAT);
                } catch (IOException ex) {
                    Logger.getLogger(OnSiteTesting.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(OnSiteTesting.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public VerifyQrCodePage getVerifyQrCodePage() {
        return this.theQrVerificationView;
    }

    public String verifyQrCode(String qrCode) {
        String result = homeBookingCollection.verifyQrCode(qrCode);
        return result;
    }

    public String[] bookHomeBooking(String customerId, HomeBookingRatStatus bookingStatus) throws IOException, InterruptedException {
        String[] qrCodeAndUrl = homeBookingCollection.addHomeBooking(customerId, bookingStatus);
        return qrCodeAndUrl;

    }

}
