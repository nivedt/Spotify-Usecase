package com.ttechlab.spotifydata.client;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONArray;

public class SpotifyApiClient {

	public static void main(String[] args) {
		
		String accessToken = "YOUR_ACCESS_TOKEN";
		String playlistUrl = "https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/tracks";

		try {
			// Create an HTTP client
			CloseableHttpClient httpClient = HttpClients.createDefault();

			// Create an HTTP GET request with the playlist URL
			HttpGet request = new HttpGet(playlistUrl);

			// Add the access token to the request headers
			request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

			// Send the request and get the response
			CloseableHttpResponse response = httpClient.execute(request);

			// Get the response body as an input stream
			InputStream responseBody = response.getEntity().getContent();

			// Convert the input stream to a string
			String jsonResponse = convertInputStreamToString(responseBody);
			
			/* Uncomment to check if the JSON response is availavable in the console*/
//			System.out.println(jsonResponse);
			
			// Assuming you have the JSON response stored in a string variable called "response"
			JSONObject responseGot = new JSONObject(jsonResponse);

            // Get the "items" array from the JSON response
			JSONArray itemsArray = responseGot.getJSONArray("items");

			// Iterate over each item in the array
			for (int i = 0; i < itemsArray.length(); i++) {
			    JSONObject item = itemsArray.getJSONObject(i).getJSONObject("track");
			    
			    System.out.println(item.get("name"));
			}

				// Close the HTTP client and response
				response.close();
				httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String convertInputStreamToString(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}
}
