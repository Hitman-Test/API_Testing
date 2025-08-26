package sat2farmAPI;

	
	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.URL;
	
	
	public class Delete_Farm_Script {
		// API key and base URL
	    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
	    private static final String BASE_URL = "https://micro.satyukt.com/deletePolygon";
	    // List of farm IDs you want to delete
	    private static final int[] farmIDs = {124032, 123964, 123965,123957}; // Add your farm IDs here
	    public static void main(String[] args) {
	        for (int farmID : farmIDs) {
	            try {
	                // Constructing the URL for the API call
	                String urlString = BASE_URL + "?key=" + API_KEY + "&farmID=" + farmID;
	                URL url = new URL(urlString);
	                // Opening the connection
	                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	                connection.setRequestMethod("GET");
	                // Checking the response code
	                int responseCode = connection.getResponseCode();
	                if (responseCode == HttpURLConnection.HTTP_OK) { // success
	                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                    String inputLine;
	                    StringBuffer response = new StringBuffer();
	                    while ((inputLine = in.readLine()) != null) {
	                        response.append(inputLine);
	                    }
	                    in.close();
	                    // Print response or handle success
	                    System.out.println("Farm ID " + farmID + " deleted successfully.");
	                } else {
	                    System.out.println("Failed to delete Farm ID " + farmID + ". Status code: " + responseCode);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}


