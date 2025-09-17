package pestAndDieseaseData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultipleFarmIdAndApiKeyScript {

	private static final String BASE_URL = "https://micro.satyukt.com/cropadvisory/info";
	private static final String LANGUAGE = ""; // e.g. "en" or "mr"

	// Date format: "03-Apr-2025"
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

	public static void main(String[] args) throws Exception {
		HttpClient client = HttpClient.newHttpClient();

		// ============================================
		// इथे तुमचे API keys आणि संबंधित farm IDs specify करा
		// ============================================
		
		
		Map<String, List<String>> apiKeyToFarmIds = new LinkedHashMap<>();
		apiKeyToFarmIds.put("9iQmP5MpxekitLp7O04PmTjLEHIo5pJW7rMEAC6EIzM=", List.of("97905", "97974"));
		apiKeyToFarmIds.put("oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=", List.of("124740", "124772"));
		// अजून हवे असल्यास असेच add करा

		// ============================================

		for (Map.Entry<String, List<String>> entry : apiKeyToFarmIds.entrySet()) {
			String apiKey = entry.getKey();
			List<String> farmIds = entry.getValue();

			System.out.println("==========================================================");
			System.out.println("🔑 Using API Key: " + apiKey);
			System.out.println("===========================================================");

			for (String farmId : farmIds) {
				String url = BASE_URL + "?key=" + apiKey + "&farm_id=" + farmId + "&language=" + LANGUAGE;

				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				System.out.println("Farm ID: " + farmId);
				System.out.println("Status: " + response.statusCode());
				System.out.println("Response Body: ");
				System.out.println(response.body()); // raw response print
				System.out.println("------------------------------------");

				if (response.statusCode() == 200) {
					String body = response.body();

					// regex ने Date range शोधणे
					Pattern pattern = Pattern
							.compile("\"Date\"\\s*:\\s*\"(\\d{2}-[A-Za-z]{3}-\\d{4}) to (\\d{2}-[A-Za-z]{3}-\\d{4})\"");
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
}
