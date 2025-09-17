package unlockFarmsScript;

	
//	import java.io.IOException;
//	import java.net.URI;
//	import java.net.URLEncoder;
//	import java.net.http.HttpClient;
//	import java.net.http.HttpRequest;
//	import java.net.http.HttpResponse;
//	import java.nio.charset.StandardCharsets;
//	import java.time.Duration;
//	import java.util.List;
//
//	public class UnlockFarm {
//		
//		// API - https://micro.satyukt.com/internal/premium?farm_id=124729&lockstatus=1&mode=Testing&expiry=6
//		
//	    private static final String BASE = "https://micro.satyukt.com/internal/premium";
//	    private static final String MODE = "Testing";   // Change the Mode of Payment
//	    private static final String EXPIRY = "6";       // Change the Expiry as per requirement 
//
//	    public static void main(String[] args) {
//	    	
//	    	
//	    	List<String> farmIds = List.of("124740", "124639");  // Farm id Lists
//
//	    
//	        HttpClient client = HttpClient.newBuilder()
//	                .connectTimeout(Duration.ofSeconds(10))
//	                .build();
//
//	        for (String farmId : farmIds) {
//	            callApi(client, farmId);
//	            try {
//	                Thread.sleep(200); // छोटा delay (API rate-limit टाळायला)
//	            } catch (InterruptedException e) {
//	                Thread.currentThread().interrupt();
//	            }
//	        }
//	    }
//
//	    private static void callApi(HttpClient client, String farmId) {
//	        try {
//	            String query = "farm_id=" + URLEncoder.encode(farmId, StandardCharsets.UTF_8)
//	                    + "&lockstatus=1&mode=" + URLEncoder.encode(MODE, StandardCharsets.UTF_8)
//	                    + "&expiry=" + URLEncoder.encode(EXPIRY, StandardCharsets.UTF_8);
//
//	            URI uri = URI.create(BASE + "?" + query);
//
//	            HttpRequest request = HttpRequest.newBuilder()
//	                    .uri(uri)
//	                    .GET()
//	                    .timeout(Duration.ofSeconds(20))
//	                    .build();
//
//	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//	            System.out.printf("farm_id=%s -> %d : %s%n",
//	                    farmId, response.statusCode(), trim(response.body(), 200));
//
//	        } catch (IOException | InterruptedException e) {
//	            System.err.printf("Error for farm_id=%s : %s%n", farmId, e.getMessage());
//	        }
//	    }
//
//	    private static String trim(String s, int max) {
//	        if (s == null) return "";
//	        return s.length() <= max ? s : s.substring(0, max) + "...";
//	    }
//	}

	import java.net.http.*;
	import java.net.*;
	import java.util.*;

	public class UnlockFarm {
	    public static void main(String[] args) throws Exception {
	        List<String> farmIds = List.of("124740", "124639"); // तुमचे IDs इथे टाका
	        HttpClient client = HttpClient.newHttpClient();

	        for (String id : farmIds) {
	            String url = "https://micro.satyukt.com/internal/premium?farm_id=" + id +
	                         "&lockstatus=1&mode=Testing&expiry=6";
	            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
	            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
	            System.out.println("farm_id=" + id + " -> " + resp.statusCode() + " : " + resp.body());
	        }
	    }
	}

	
//	import java.net.http.*;
//	import java.net.*;
//	import java.util.*;
//
//	public class UnlockFarm {
//	    public static void main(String[] args) throws Exception {
//	        List<String> farmIds = List.of("124740", "124639"); // तुमचे IDs इथे टाका
//	        HttpClient client = HttpClient.newHttpClient();
//
//	        // Header print करा
//	        System.out.printf("%-10s | %-6s | %-50s%n", "Farm ID", "Status", "Response");
//	        System.out.println("---------------------------------------------------------------");
//
//	        for (String id : farmIds) {
//	            String url = "https://micro.satyukt.com/internal/premium?farm_id=" + id +
//	                         "&lockstatus=1&mode=Testing&expiry=6";
//	            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
//	            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
//
//	            // Response लांब असेल तर shorten करा
//	            String body = resp.body();
//	            if (body.length() > 50) {
//	                body = body.substring(0, 47) + "...";
//	            }
//
//	            // Table row print
//	            System.out.printf("%-10s | %-6d | %-50s%n", id, resp.statusCode(), body);
//	        }
//	    }
//	}


