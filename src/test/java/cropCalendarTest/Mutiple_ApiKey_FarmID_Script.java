package cropCalendarTest;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// API Configuration class
class ApiConfig {
	String name;
	String farmDetailUrl;
	String cropCalendarUrl;
	String apiKey;
	List<String> farmIds;

	ApiConfig(String name, String farmDetailUrl, String cropCalendarUrl, String apiKey, List<String> farmIds) {
		this.name = name;
		this.farmDetailUrl = farmDetailUrl;
		this.cropCalendarUrl = cropCalendarUrl;
		this.apiKey = apiKey;
		this.farmIds = farmIds;
	}
}

public class Mutiple_ApiKey_FarmID_Script {

	// Change this path as per your system
	private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData";

	public static void main(String[] args) throws Exception {
		HttpClient client = HttpClient.newHttpClient();

		// Example: multiple APIs config
		List<ApiConfig> apis = List.of(new ApiConfig("SatyuktFarm",
				"https://micro.satyukt.com/showPolygon?farmID=%s&key=%s",
				"https://micro.satyukt.com/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=%s",
				"9iQmP5MpxekitLp7O04PmTjLEHIo5pJW7rMEAC6EIzM=", // 🔑 तुझा खरा key टाक
				List.of("75794", "75798", "97905", "97922")),
				new ApiConfig("SatyuktFarm2", "https://micro.satyukt.com/showPolygon?farmID=%s&key=%s",
						"https://micro.satyukt.com/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=%s",
						"oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=", // 🔑 दुसरा key
						List.of("124656", "124630", "119091", "124740", "124605")));

		// Supported languages
		List<String> languages = List.of("en", "hi", "mr");

		// Generate file name with timestamp
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String fileName = OUTPUT_FOLDER + "farm_crop_calendar_" + timestamp + ".csv";

		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			// Header row
			writer.println(
					"api_name,farm_id,lang,crop_type,sowing_date,days_from_sowing,farm_detail_api_url,crop_calendar_api_url,crop_calendar_response,comment");

			for (ApiConfig api : apis) {
				for (String farmId : api.farmIds) {
					for (String lang : languages) {

						String farmDetailUrl = String.format(api.farmDetailUrl, farmId, api.apiKey);
						HttpRequest farmRequest = HttpRequest.newBuilder().uri(URI.create(farmDetailUrl)).GET().build();

						HttpResponse<String> farmResponse = client.send(farmRequest,
								HttpResponse.BodyHandlers.ofString());

						String cropType = "";
						String sowingDate = "";
						String daysFromSowing = "";
						String cropCalendarUrl = "";
						String comment = "";
						String calendarData = "";

						if (farmResponse.statusCode() == 200) {
							JsonObject farmJson = JsonParser.parseString(farmResponse.body()).getAsJsonObject();

							// crop_type
							if (farmJson.has("crop_type") && !farmJson.get("crop_type").isJsonNull()) {
								cropType = farmJson.get("crop_type").getAsString();
							} else {
								comment += "crop_type missing; ";
							}

							// Sowing_date
							if (farmJson.has("Sowing_date") && !farmJson.get("Sowing_date").isJsonNull()) {
								sowingDate = farmJson.get("Sowing_date").getAsString();
								try {
									LocalDate sowing = LocalDate.parse(sowingDate,
											DateTimeFormatter.ofPattern("yyyy-MM-dd"));
									LocalDate today = LocalDate.now();
									long diff = ChronoUnit.DAYS.between(sowing, today);
									daysFromSowing = String.valueOf(diff);
								} catch (Exception e) {
									comment += "invalid sowing_date format; ";
								}
							} else {
								comment += "sowing_date missing; ";
							}

							// Build Crop Calendar URL
							cropCalendarUrl = String.format(api.cropCalendarUrl, api.apiKey, farmId,
									daysFromSowing.isEmpty() ? "" : daysFromSowing, cropType.isEmpty() ? "" : cropType,
									lang);

							// Call Crop Calendar API
							HttpRequest calendarRequest = HttpRequest.newBuilder().uri(URI.create(cropCalendarUrl))
									.GET().build();

							HttpResponse<String> calendarResponse = client.send(calendarRequest,
									HttpResponse.BodyHandlers.ofString());

							calendarData = calendarResponse.statusCode() == 200 ? calendarResponse.body()
									: "ERROR " + calendarResponse.statusCode();
						} else {
							comment = "Farm detail API failed";
						}

						// Clean JSON for CSV (escape quotes)
						String safeCalendarData = calendarData.replaceAll("[\\r\\n]+", " ") // remove newlines
								.replace("\"", "\"\""); // escape quotes for CSV

						// Write row
						writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
								api.name, farmId, lang, cropType, sowingDate, daysFromSowing, farmDetailUrl,
								cropCalendarUrl, safeCalendarData, comment.trim());
					}
				}
			}
		}

		System.out.println("✅ CSV file created: " + fileName);
	}
}
