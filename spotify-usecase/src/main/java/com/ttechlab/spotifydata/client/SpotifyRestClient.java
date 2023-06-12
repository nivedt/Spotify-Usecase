package com.ttechlab.spotifydata.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SpotifyRestClient {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set the request body parameters
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        
        /* Add your Client ID and Client Secret got from Spotify
           Follow the Documentation for getting the two 
           "https://developer.spotify.com/documentation/web-api/tutorials/getting-started#request-an-access-token"
        */
        
        requestBody.add("client_id", "YOUR_CLIENT_ID");
        requestBody.add("client_secret", "YOUR_CLIENT_SECRET");

        // Create the HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://accounts.spotify.com/api/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Process the response
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            
        /* The access token along will be printed in the console */
            System.out.println("Access token: " + responseBody);
        } else {
            System.out.println("Failed to obtain access token. Status code: " + responseEntity.getStatusCodeValue());
        }
    }
}
