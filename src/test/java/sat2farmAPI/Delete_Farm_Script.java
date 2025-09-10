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
	    private static final int[] farmIDs = {115165, 115167, 115174, 115180, 115183, 115185, 115241, 115338, 115343, 115345, 115347, 115359, 115360, 115361, 115595, 115596, 115597, 115672, 115677, 115686, 115687, 115688, 115689, 115731, 115812, 115871, 115875, 115877, 119298, 119436, 119444, 119446, 119447, 119448, 119456, 119457, 119458, 119596, 119872, 120476, 120626, 120627, 120782, 120787, 120830, 120900, 120913, 120914, 120917, 121297, 121692, 121743, 121745, 121822, 121847, 121860, 121863, 122028, 122096, 122340, 122343, 122476, 122481, 122500, 122501, 122502, 122525, 123045, 123052, 123054, 123060, 123089, 123102, 123138, 123141, 123277, 123328, 123347, 123353, 123371, 123409, 123411, 123440, 123441, 123701, 123714, 123726, 123770, 123822, 123956, 124132, 124133, 124274, 124317, 124319, 124403, 124423, 124431, 124434, 124436, 124437, 124438, 124440, 124446, 124491, 124492, 124493, 124578}; // Add your farm IDs here
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


