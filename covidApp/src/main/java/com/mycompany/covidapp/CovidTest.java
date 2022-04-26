/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.covidapp;

import com.mycompany.covidapp.TestType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author sooyewlim
 */
public class CovidTest {
    TestType testType;
    private String patientId;
    private String bookingId;
    private String covidTestId;
    private final String myApiKey = "HN9t9JCtwNhcCP6mnWBgg6prpcKrPw";
    private final String rootUrl = "https://fit3077.com/api/v1";

    public CovidTest(TestType testType, String patientId, String bookingId) throws IOException, InterruptedException {
        this.testType = testType;
        this.patientId = patientId;
        this.bookingId = bookingId;
        String jsonString="";
        jsonString="{\"type\":"+"\""+testType+"\""+",\"patientId\":"+"\""+patientId+"\""+",\"bookingId\":"+"\""+bookingId+"\""+",\"result\":"+"\""+TestResult.PENDING+"\""+"}";
        String covidTestUrl = rootUrl + "/covid-test";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(covidTestUrl))
            .POST(HttpRequest.BodyPublishers.ofString(jsonString))
            .setHeader("Authorization", myApiKey)
            .header("Content-Type","application/json")
            .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    
}
