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
	    private static final int[] farmIDs = {116546, 116973, 117011, 117076, 117339, 118412, 118413, 118434, 118435, 118436, 118438, 118441, 118483, 118486, 118488, 118490, 118495, 118498, 118501, 118506, 118508, 118995, 119091, 119252, 119255, 124605, 124606, 124630, 124639, 124649, 124656, 124740, 124772, 124798, 124876, 124892, 124896, 124913, 124914, 125079, 125125, 125186, 125187, 125189, 125191, 125192, 125193, 125201, 125203, 125204, 125205, 125206, 125207, 125208, 125209, 125210, 125218, 125219, 125220, 125221, 125222, 125223, 125224, 125225, 125234, 125235, 125237, 125238, 125252, 125276, 125284}; // Add your farm IDs here
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


