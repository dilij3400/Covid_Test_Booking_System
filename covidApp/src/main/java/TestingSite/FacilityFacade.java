/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestingSite;

import Application.AdminBookingPanel;
import Application.NewJFrame;
import Application.OnSiteBookingPage;
import Application.OnSiteTesting;
import Application.OnSiteTestingVerificationPage;
import Application.OnlineOnSiteTestingBooking;
import Application.SearchTestingSiteView;
import Application.BookingModificationPage;
import Application.PhoneCallBookingModification;
import Booking.CareTaker;
import Booking.Memento;
import Booking.OnSiteBooking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
public class FacilityFacade {

    private SearchTestingSiteView theSearchView;
    private OnSiteBookingPage theBookView;
    private OnSiteTestingVerificationPage theVerifyView;
    private BookingModificationPage theModifyBookingView;
    private OnlineOnSiteTestingBooking theOnlineBookingView;
    private PhoneCallBookingModification thePhoneCallModificationView;
    private AdminBookingPanel theAdminBookingPanelView;
    //private static final String myApiKey = NewJFrame.apiKey;
    private static final String myApiKey = "nMTd7jFGPtbhJ6gpkMtRGHRQfwbj86";
    private static final String rootUrl = "https://fit3077.com/api/v2";
    OffShoreTestingSiteCollection offShoreTestingSiteCollection;
    TestingSiteDataSourceCollection testingSiteDataSourceCollection;

    public FacilityFacade() throws Exception {
        this.offShoreTestingSiteCollection = OffShoreTestingSiteCollection.getInstance();
        this.testingSiteDataSourceCollection = TestingSiteDataSourceCollection.getInstance();
        this.theSearchView = new SearchTestingSiteView();
        this.theBookView = new OnSiteBookingPage();
        this.theOnlineBookingView = new OnlineOnSiteTestingBooking();
        this.theVerifyView = new OnSiteTestingVerificationPage();
        this.theModifyBookingView = new BookingModificationPage();
        this.thePhoneCallModificationView = new PhoneCallBookingModification();
        this.theSearchView.addSearchListener(new SearchListener());
        this.theAdminBookingPanelView = new AdminBookingPanel();
        this.theBookView.addBookListener(new OnSiteBookingListener());
        this.theVerifyView.addVerifyListener(new OnSiteVerifyListener());
        this.theOnlineBookingView.addBookListener(new OnlineOnSiteBookingListener());
        this.theModifyBookingView.addModifyBookingListener(new ModifyBookingListener());
        this.theModifyBookingView.addVerifyBookingListener(new VerifyBookingListener());
        this.theModifyBookingView.addRevertBookingListener(new RevertBookingListener());
        this.theAdminBookingPanelView.addRefreshListener(new RefreshListener());
    }

    public BookingModificationPage getTheModifyBookingView() {
        return theModifyBookingView;
    }

    class SearchListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String suburbName = theSearchView.getSuburbName();
            String facilityType = theSearchView.getFacilityType();
            ArrayList<OffShoreTestingSite> searchTestingSiteResult = searchTestingSite(suburbName, facilityType);
            theSearchView.updateView(searchTestingSiteResult);
        }
    }

    class RefreshListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String facilityId = theAdminBookingPanelView.getFacilityId();
            ArrayList<OnSiteBooking> onSiteBookings = offShoreTestingSiteCollection.getBookingFromFacility(facilityId);
            theAdminBookingPanelView.updateRefreshResultView(onSiteBookings);
        }
    }

    class OnlineOnSiteBookingListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String facilityId = theOnlineBookingView.getFacilityId();
            String customerId = theOnlineBookingView.getCustomerId();
            String bookingDate = theOnlineBookingView.getBookingDate();
            String bookingTime = theOnlineBookingView.getBookingTime();
            String bookingResult = bookOnSiteBooking(facilityId, customerId, bookingDate, bookingTime);
            theOnlineBookingView.updateView(bookingResult);
        }
    }

    class OnSiteBookingListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String facilityId = theBookView.getFacilityId();
            String customerId = theBookView.getCustomerId();
            String bookingDate = theBookView.getBookingDate();
            String bookingTime = theBookView.getBookingTime();
            String bookingResult = bookOnSiteBooking(facilityId, customerId, bookingDate, bookingTime);
            theBookView.updateView(bookingResult);
        }
    }

    class OnSiteVerifyListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String facilityId = theVerifyView.getFacilityId();
            String bookingId = theVerifyView.getBookingId();
            OnSiteBooking onSiteBooking = verifyOnSiteBooking(facilityId, bookingId);
            String verifyResult;
            if (onSiteBooking != null) {
                verifyResult = "This is a valid booking";
                OnSiteTesting obj = new OnSiteTesting();
                obj.setBooking(onSiteBooking);
                obj.setVisible(true);

            } else {
                verifyResult = "This is not a valid bookingid ";
            }
            theVerifyView.updateView(verifyResult);
        }
    }

    class VerifyBookingListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String bookingId = theModifyBookingView.getBookingId();
            Boolean verifyBooking = verifyOnSiteBookingModification(bookingId);
            if (verifyBooking == false) {
                theModifyBookingView.updateBookingResultView("Wrong booking id or this booking has already tested");
            } else {

                CareTaker careTaker = testingSiteDataSourceCollection.getCareTaker();
                String result = "valid booking id you can modify the date or venue for this booking \n";
                int i = 1;
                for (Memento node : careTaker.getPreviousBooking(bookingId)) {
                    result += Integer.toString(i) + " " + node + "\n";
                    i += 1;
                }
                theModifyBookingView.updateBookingResultView(result);

            }
        }
    }

    class RevertBookingListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String bookingId = theModifyBookingView.getBookingId();
            OnSiteBooking onSiteBooking = searchOnSiteBooking(bookingId);
            String currentFacilityId = onSiteBooking.getFacilityId();
            String revertNo = theModifyBookingView.getRevertNo();
            OffShoreTestingSiteDataSource offShoreTestingSiteDataSource = testingSiteDataSourceCollection.searchId(currentFacilityId);
            ArrayList<Memento> previousBooking = testingSiteDataSourceCollection.getCareTaker().getPreviousBooking(bookingId);
            Memento memento = previousBooking.get(Integer.parseInt(revertNo) - 1);
            try {
                onSiteBooking.restoreFromMemento(memento);
            } catch (IOException ex) {
                Logger.getLogger(FacilityFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FacilityFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FacilityFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
            theModifyBookingView.updateRevertResultView(onSiteBooking.toString());

        }
    }

    class ModifyBookingListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String result = "";
            String facilityId = theModifyBookingView.getFacilityId();
            String dateToModify = theModifyBookingView.getBookingDate();
            String timeToModify = theModifyBookingView.getBookingTime();
            String bookingId = theModifyBookingView.getBookingId();
            try {
                result = modifyOnSiteBooking(bookingId, facilityId, dateToModify, timeToModify);
            } catch (IOException ex) {
                Logger.getLogger(FacilityFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FacilityFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
            theModifyBookingView.updateBookingModificationResultView(result);

        }
    }

    public SearchTestingSiteView getTheSearchView() {
        return theSearchView;
    }

    public OnSiteBookingPage getTheBookView() {
        return theBookView;
    }

    public OnSiteTestingVerificationPage getOnSiteTestingVerificationPage() {
        return theVerifyView;
    }

    public BookingModificationPage getBookingModificationPage() {
        return theModifyBookingView;
    }

    public AdminBookingPanel getTheAdminBookingPanelView() {
        return theAdminBookingPanelView;
    }

    //this method is to add the on site book by providing faility id and customer id and it will be pushed to the web service
    public String bookOnSiteBooking(String facilityId, String customerId, String bookingDate, String bookingTime) {
        String bookingResult = "";
        String usersUrl = rootUrl + "/user";
        HttpResponse response;

        // searching for a specific facility given facility id.
        OffShoreTestingSite testingSite = offShoreTestingSiteCollection.searchId(facilityId);
        // Check if facility exists

        if (testingSite == null) {
            bookingResult = "Facility does not exist";
        } else if (testingSite != null) {

            // perform GET request of all customers.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(usersUrl))
                    .setHeader("Authorization", myApiKey)
                    .GET()
                    .build();

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());

                ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body().toString(), ObjectNode[].class);

                // Iterate through each customer and cross check if customer id is equal to input id.
                for (ObjectNode node : jsonNodes) {
                    String id = node.get("id").toString();
                    String result = id.replaceAll("^\"|\"$", "");
                    if (!result.equals(customerId)) {

                        bookingResult = "Customer does not exist";
                    } else {

                        //testingSiteDataSourceCollection = TestingSiteDataSourceCollection.getInstance();
                        OffShoreTestingSiteDataSource offShoreTestingSiteDataSource = testingSiteDataSourceCollection.searchId(facilityId);

                        // Make booking
                        response = offShoreTestingSiteDataSource.addBooking(customerId, facilityId, bookingDate, bookingTime);

                        if (response.statusCode() == 201) {
                            ObjectNode jsonNode = new ObjectMapper().readValue(response.body().toString(), ObjectNode.class);
                            bookingResult = "Booking created successfully, your PIN number is : " + jsonNode.get("smsPin") + " BookingId: " + jsonNode.get("id");

                        } else if (response.statusCode() == 404) {
                            bookingResult = "A customer and/or testing site with the provided ID was not found.";
                        } else {
                            bookingResult = "Error";
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                Logger.getLogger(FacilityFacade.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return bookingResult;
    }

    public String modifyOnSiteBooking(String bookingId, String changeToFacilityId, String changeToBookingDate, String changeToBookingTime) throws IOException, InterruptedException {
        String result;

        OnSiteBooking currentBooking = offShoreTestingSiteCollection.searchBooking(bookingId);
        String currentBookingFacility = currentBooking.getFacilityId();
        OffShoreTestingSiteDataSource currentOffShoreTestingSiteDataSource = testingSiteDataSourceCollection.searchId(currentBookingFacility);
        //if user wish to modify booking date only
        if (changeToFacilityId.length() == 0 && changeToBookingDate.length() != 0) {
            result = currentOffShoreTestingSiteDataSource.modifyBookingDateTime(bookingId, changeToBookingDate, changeToBookingTime);
            //if user wish to modify facility only 
        } else if (changeToFacilityId.length() != 0 && changeToBookingDate.length() == 0) {
            OffShoreTestingSiteDataSource toModifyOffShoreTestingSiteDataSource = testingSiteDataSourceCollection.searchId(changeToFacilityId);
            OnSiteBooking bookingToMove = currentOffShoreTestingSiteDataSource.modifyBookingFacility(bookingId, changeToFacilityId);
            if (bookingToMove == null) {
                result = "booking can't be modified";
            } else {
                toModifyOffShoreTestingSiteDataSource.updateBooking(bookingToMove);
                result = "booking modified";
            }
        } //if user wish to modify both
        else {
            OffShoreTestingSiteDataSource toModifyOffShoreTestingSiteDataSource = testingSiteDataSourceCollection.searchId(changeToFacilityId);
            OnSiteBooking bookingToMove = currentOffShoreTestingSiteDataSource.modifyBookingDateTimeFacility(bookingId, changeToFacilityId, changeToBookingDate, changeToBookingTime);
            if (bookingToMove == null) {
                result = "booking can't be modified";
            } else {
                toModifyOffShoreTestingSiteDataSource.updateBooking(bookingToMove);
                result = "booking modified";
            }

        }

        return result;
    }

    //done
    public ArrayList<OffShoreTestingSite> searchTestingSite(String suburb, String facility) {
        ArrayList<OffShoreTestingSite> searchTestingSiteResult = OffShoreTestingSiteCollection.getInstance().search(suburb, facility);
        return searchTestingSiteResult;
    }

    public OnSiteBooking searchOnSiteBooking(String bookingId) {
        OnSiteBooking booking = offShoreTestingSiteCollection.searchBooking(bookingId);
        return booking;
    }

    public OnSiteBooking verifyOnSiteBooking(String facilityId, String bookingId) {
        OffShoreTestingSite testingSite = offShoreTestingSiteCollection.searchId(facilityId);
        OnSiteBooking currentUserBooking = null;
        if (testingSite != null) {
            currentUserBooking = testingSite.searchBooking(bookingId);
        }
        return currentUserBooking;
    }

    public Boolean verifyOnSiteBookingModification(String bookingIdOrPin) {
        for (OffShoreTestingSite node : offShoreTestingSiteCollection.getOffShoreTesting()) {
            OnSiteBooking currentUserBooking = node.searchBooking(bookingIdOrPin);
            if (currentUserBooking == null && bookingIdOrPin.length() < 8) {
                currentUserBooking = node.searchBookingPin(bookingIdOrPin);
            }
            if (currentUserBooking != null) {
                if (currentUserBooking.getStatus().equals("INITIATED")) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }
}
