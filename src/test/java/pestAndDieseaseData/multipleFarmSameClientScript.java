package pestAndDieseaseData;
	
//	import java.net.URI;
//	import java.net.http.HttpClient;
//	import java.net.http.HttpRequest;
//	import java.net.http.HttpResponse;
//	import java.time.Duration;
//	import java.util.List;
	
//	public class multipleFarmSameClientScript {
//
//	
//	    private static final String BASE_URL = "https://micro.satyukt.com/cropadvisory/info";
//	    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
//	    private static final String LANGUAGE = ""; // "en" किंवा "mr" द्यायचं असल्यास इथे भरा
//
//	    public static void main(String[] args) throws Exception {
//	        // तुमचे farm ids इथे द्या
//	        List<String> farmIds = List.of("127740", "124639", "124606");
//
//	        HttpClient client = HttpClient.newBuilder()
//	                .connectTimeout(Duration.ofSeconds(20))
//	                .build();
//
//	        for (String farmId : farmIds) {
//	            String url = BASE_URL
//	                    + "?key=" + API_KEY
//	                    + "&farm_id=" + farmId
//	                    + "&language=" + LANGUAGE;
//
//	            HttpRequest request = HttpRequest.newBuilder()
//	                    .uri(URI.create(url))
//	                    .timeout(Duration.ofSeconds(30))
//	                    .GET()
//	                    .build();
//
//	            System.out.println("Fetching farm_id=" + farmId);
//
//	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//	            System.out.println("Status: " + response.statusCode());
//	            System.out.println("Response: " + response.body());
//	            System.out.println("======================================");
//	        }
//	    }
//	}

	
	import java.net.URI;
	import java.net.http.HttpClient;
	import java.net.http.HttpRequest;
	import java.net.http.HttpResponse;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.List;
	import java.util.Locale;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;

	public class multipleFarmSameClientScript {
	    private static final String BASE_URL = "https://micro.satyukt.com/cropadvisory/info";
	    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
	    private static final String LANGUAGE = "";

	    // Date format: "03-Apr-2025"
	    private static final DateTimeFormatter DATE_FORMAT =
	            DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

	    public static void main(String[] args) throws Exception {
	        List<String> farmIds = List.of("118483", "124605", "124649");

	        HttpClient client = HttpClient.newHttpClient();

	        for (String farmId : farmIds) {
	            String url = BASE_URL
	                    + "?key=" + API_KEY
	                    + "&farm_id=" + farmId
	                    + "&language=" + LANGUAGE;

	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            System.out.println("Farm ID: " + farmId);
	            System.out.println("Status: " + response.statusCode());
	            System.out.println("Response Body: ");
	            System.out.println(response.body());   // full API response print
	            System.out.println("------------------------------------");

	            if (response.statusCode() == 200) {
	                String body = response.body();

	                // regex ने "Date": "03-Apr-2025 to 07-Apr-2025" शोधणे
	                Pattern pattern = Pattern.compile("\"Date\"\\s*:\\s*\"(\\d{2}-[A-Za-z]{3}-\\d{4}) to (\\d{2}-[A-Za-z]{3}-\\d{4})\"");
	                Matcher matcher = pattern.matcher(body);

	                if (matcher.find()) {
	                    String startStr = matcher.group(1);
	                    String endStr = matcher.group(2);

	                    LocalDate start = LocalDate.parse(startStr, DATE_FORMAT);
	                    LocalDate end = LocalDate.parse(endStr, DATE_FORMAT);
	                    LocalDate today = LocalDate.now();

	                    System.out.println("Date Range Found: " + start + " to " + end);
	                    if (!today.isBefore(start) && !today.isAfter(end)) {
	                        System.out.println("✅ PASS (today " + today + " is within range)");
	                    } else {
	                        System.out.println("❌ FAIL (today " + today + " is outside range)");
	                    }
	                } else {
	                    System.out.println("⚠️ 'Date' field not found in response.");
	                }
	            } else {
	                System.out.println("⚠️ API error, body already shown above.");
	            }

	            System.out.println("====================================\n");
	        }
	    }
	}

	


