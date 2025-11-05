package addFarm_Test;


import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import org.apache.commons.csv.*;
import org.json.JSONObject;

public class AutoUnlock_Farm {
	


	

	    private static final String API_URL = "https://micro.satyukt.com/postjson2?name=%s&coordinates=%s&croptype=%s&category=%s&sowingdate=%s&crop_variety=%s&key=%s";
	    private static final String LOCK_API_URL = "https://micro.satyukt.com/internal/premium?farm_id=%s&lockstatus=1&mode=Testing&expiry=6";  // Lock API URL
	    private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData/Add Farm/Output file/AutoUnlockFarm/";  

	    public static void main(String[] args) {
	        try {
	            ZonedDateTime indiaTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
	            String formattedDate = indiaTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

	            File outputFile = new File(OUTPUT_FOLDER + "addFarm_responses_" + formattedDate + ".csv");
	            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
	            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Coordinate", "Crop Name", "Variety", "Sowing Date", "API Key", "Category", "Timestamp", "Farm Status"));

	            Reader reader = new FileReader("/home/user/Downloads/Testing/AutmatedScriptTest/TestData/Add Farm/Input File/Dummy Data Add Farm.csv");
	            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader);

	            HttpClient client = HttpClient.newHttpClient();
	            for (CSVRecord record : csvParser) {
	                String farmName = record.get("name");
	                String coordinates = record.get("coordinates");
	                String cropType = record.get("croptype");
	                String category = record.get("category");
	                String sowingDate = record.get("sowingdate");
	                String cropVariety = record.get("crop_variety");
	                String apiKey = record.get("key");

	                // Handle missing parameters
	                farmName = farmName != null ? farmName : "";
	                coordinates = coordinates != null ? coordinates : "[]";
	                cropType = cropType != null ? cropType : "";
	                category = category != null ? category : "";
	                sowingDate = sowingDate != null ? sowingDate : "";
	                cropVariety = cropVariety != null ? cropVariety : "";
	                apiKey = apiKey != null ? apiKey : "";

	                // URL encode parameters
	                String encodedCoordinates = URLEncoder.encode(coordinates, StandardCharsets.UTF_8);
	                String encodedFarmName = URLEncoder.encode(farmName, StandardCharsets.UTF_8);
	                String encodedCropType = URLEncoder.encode(cropType, StandardCharsets.UTF_8);
	                String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);
	                String encodedSowingDate = URLEncoder.encode(sowingDate, StandardCharsets.UTF_8);
	                String encodedCropVariety = URLEncoder.encode(cropVariety, StandardCharsets.UTF_8);
	                String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);

	                // Format the request URL for adding farm
	                String requestUrl = String.format(API_URL, encodedFarmName, encodedCoordinates, encodedCropType, encodedCategory, encodedSowingDate, encodedCropVariety, encodedApiKey);

	                // Create request object for adding farm
	                HttpRequest request = HttpRequest.newBuilder()
	                        .uri(URI.create(requestUrl))
	                        .header("Content-Type", "application/json")
	                        .build();

	                // Send the request to add farm
	                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	                String responseBody = response.body();
	                JSONObject jsonResponse = new JSONObject(responseBody);
	                String id = jsonResponse.optString("ID", "No ID found");

	                // Default Farm Status
	                String farmStatus = "Failed to Add";  // Default farm status

	                // If the farm is added successfully
	                if (!id.equals("No ID found")) {
	                    farmStatus = "Added Successfully";  // Update status if farm is added

	                    // Send the lock API request to lock the farm
	                    String lockRequestUrl = String.format(LOCK_API_URL, id);  // Format lock request URL
	                    HttpRequest lockRequest = HttpRequest.newBuilder()
	                            .uri(URI.create(lockRequestUrl))
	                            .header("Content-Type", "application/json")
	                            .build();

	                    // Send the lock API request and capture the response
	                    HttpResponse<String> lockResponse = client.send(lockRequest, HttpResponse.BodyHandlers.ofString());
	                    farmStatus = lockResponse.body();  // Capture the lock API response directly into farmStatus

	                }

	                // Write data to CSV with Farm Status (lock API response)
	                csvPrinter.printRecord(id, coordinates, cropType, cropVariety, sowingDate, apiKey, category, formattedDate, farmStatus);

	                // Wait for 30 seconds before making the next API request
	                System.out.println("Waiting for 30 seconds before the next API hit...");
	                Thread.sleep(30000);
	            }

	            csvPrinter.flush();
	            writer.close();

	            System.out.println("API calls completed. CSV file is saved at: " + outputFile.getAbsolutePath());
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}



