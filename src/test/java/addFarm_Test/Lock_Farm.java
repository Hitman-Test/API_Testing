//package addFarm_Test;
//
//import java.io.*;
//import java.net.URI;
//import java.net.http.*;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.*;
//import java.time.format.DateTimeFormatter;
//import org.apache.commons.csv.*;
//import org.json.JSONObject;
//
//public class Single_Account {
//
//    private static final String API_URL = "https://micro.satyukt.com/postjson2?name=%s&coordinates=%s&croptype=%s&category=%s&sowingdate=%s&crop_variety=%s&key=%s";
//    private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData/Add Farm/Output file/";  // Path to save the output CSV file
//
//    public static void main(String[] args) {
//        try {
//            // Initialize CSVWriter to create a new CSV file
//            File outputFile = new File(OUTPUT_FOLDER + "addFarm_responses.csv");
//            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
//            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Coordinate", "Crop Name", "Variety", "Sowing Date", "API Key", "Category"));
//
//            // Reading data from a CSV file
//            Reader reader = new FileReader("/home/user/Downloads/Testing/AutmatedScriptTest/TestData/Add Farm/Input File/Dummy Data Add Farm.csv");  // Update path
//            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader);
//
//            HttpClient client = HttpClient.newHttpClient();
//            for (CSVRecord record : csvParser) {
//                // Extract data from each row in the CSV
//                String farmName = record.get("name");
//                String coordinates = record.get("coordinates");  // Coordinates as string, adjust the header as necessary
//                String cropType = record.get("croptype");
//                String category = record.get("category");
//                String sowingDate = record.get("sowingdate");
//                String cropVariety = record.get("crop_variety");
//                String apiKey = record.get("key");  // API Key for each row
//
//                // Handle empty parameters, set them to empty string if null
//                farmName = (farmName != null && !farmName.isEmpty()) ? farmName : "";
//                coordinates = (coordinates != null && !coordinates.isEmpty()) ? coordinates : "[]";
//                cropType = (cropType != null && !cropType.isEmpty()) ? cropType : "";
//                category = (category != null && !category.isEmpty()) ? category : "";
//                sowingDate = (sowingDate != null && !sowingDate.isEmpty()) ? sowingDate : "";
//                cropVariety = (cropVariety != null && !cropVariety.isEmpty()) ? cropVariety : "";
//                apiKey = (apiKey != null && !apiKey.isEmpty()) ? apiKey : "";  // Handle missing API Key
//
//                // URL Encode the parameters to avoid illegal characters in the query
//                String encodedCoordinates = URLEncoder.encode(coordinates, StandardCharsets.UTF_8);
//                String encodedFarmName = URLEncoder.encode(farmName, StandardCharsets.UTF_8);
//                String encodedCropType = URLEncoder.encode(cropType, StandardCharsets.UTF_8);
//                String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);
//                String encodedSowingDate = URLEncoder.encode(sowingDate, StandardCharsets.UTF_8);
//                String encodedCropVariety = URLEncoder.encode(cropVariety, StandardCharsets.UTF_8);
//                String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
//
//                // Format the request URL for each row
//                String requestUrl = String.format(API_URL, encodedFarmName, encodedCoordinates, encodedCropType, encodedCategory, encodedSowingDate, encodedCropVariety, encodedApiKey);
//
//                // Create the request object
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(requestUrl))
//                        .header("Content-Type", "application/json")
//                        .build();
//
//                // Send the request
//                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//                // Parse the JSON response to extract the "ID"
//                String responseBody = response.body();
//                JSONObject jsonResponse = new JSONObject(responseBody);
//                String id = jsonResponse.optString("ID", "No ID found");  // Extract ID or default if not found
//
//                // Write data to CSV
//                csvPrinter.printRecord(id, coordinates, cropType, cropVariety, sowingDate, apiKey, category);
//
//                // Wait for 30 seconds before making the next API request
//                System.out.println("Waiting for 30 seconds before the next API hit...");
//                Thread.sleep(30000);  // 30 seconds delay
//            }
//
//            // Close the CSVPrinter and BufferedWriter
//            csvPrinter.flush();
//            writer.close();
//
//            System.out.println("API calls completed. CSV file is saved at: " + outputFile.getAbsolutePath());
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}



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

public class Lock_Farm {

    private static final String API_URL = "https://micro.satyukt.com/postjson2?name=%s&coordinates=%s&croptype=%s&category=%s&sowingdate=%s&crop_variety=%s&key=%s";
    private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData/Add Farm/Output file/";  // Path to save the output CSV file

    public static void main(String[] args) {
        try {
            // Get the current date and time in IST (Asia/Kolkata time zone)
            ZonedDateTime indiaTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
            String formattedDate = indiaTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            // Initialize CSVWriter to create a new CSV file with the current timestamp
            File outputFile = new File(OUTPUT_FOLDER + "addFarm_responses_" + formattedDate + ".csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Coordinate", "Crop Name", "Variety", "Sowing Date", "API Key", "Category", "Timestamp"));

            // Reading data from a CSV file
            Reader reader = new FileReader("/home/user/Downloads/Testing/AutmatedScriptTest/TestData/Add Farm/Input File/Dummy Data Add Farm.csv");  // Update path
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader);

            HttpClient client = HttpClient.newHttpClient();
            for (CSVRecord record : csvParser) {
                // Extract data from each row in the CSV
                String farmName = record.get("name");
                String coordinates = record.get("coordinates");  // Coordinates as string, adjust the header as necessary
                String cropType = record.get("croptype");
                String category = record.get("category");
                String sowingDate = record.get("sowingdate");
                String cropVariety = record.get("crop_variety");
                String apiKey = record.get("key");  // API Key for each row

                // Handle empty parameters, set them to empty string if null
                farmName = (farmName != null && !farmName.isEmpty()) ? farmName : "";
                coordinates = (coordinates != null && !coordinates.isEmpty()) ? coordinates : "[]";
                cropType = (cropType != null && !cropType.isEmpty()) ? cropType : "";
                category = (category != null && !category.isEmpty()) ? category : "";
                sowingDate = (sowingDate != null && !sowingDate.isEmpty()) ? sowingDate : "";
                cropVariety = (cropVariety != null && !cropVariety.isEmpty()) ? cropVariety : "";
                apiKey = (apiKey != null && !apiKey.isEmpty()) ? apiKey : "";  // Handle missing API Key

                // URL Encode the parameters to avoid illegal characters in the query
                String encodedCoordinates = URLEncoder.encode(coordinates, StandardCharsets.UTF_8);
                String encodedFarmName = URLEncoder.encode(farmName, StandardCharsets.UTF_8);
                String encodedCropType = URLEncoder.encode(cropType, StandardCharsets.UTF_8);
                String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);
                String encodedSowingDate = URLEncoder.encode(sowingDate, StandardCharsets.UTF_8);
                String encodedCropVariety = URLEncoder.encode(cropVariety, StandardCharsets.UTF_8);
                String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);

                // Format the request URL for each row
                String requestUrl = String.format(API_URL, encodedFarmName, encodedCoordinates, encodedCropType, encodedCategory, encodedSowingDate, encodedCropVariety, encodedApiKey);

                // Create the request object
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(requestUrl))
                        .header("Content-Type", "application/json")
                        .build();

                // Send the request
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Parse the JSON response to extract the "ID"
                String responseBody = response.body();
                JSONObject jsonResponse = new JSONObject(responseBody);
                String id = jsonResponse.optString("ID", "No ID found");  // Extract ID or default if not found

                // Write data to CSV, including the timestamp
                csvPrinter.printRecord(id, coordinates, cropType, cropVariety, sowingDate, apiKey, category, formattedDate);

                // Wait for 30 seconds before making the next API request
                System.out.println("Waiting for 30 seconds before the next API hit...");
                Thread.sleep(30000);  // 30 seconds delay
            }

            // Close the CSVPrinter and BufferedWriter
            csvPrinter.flush();
            writer.close();

            System.out.println("API calls completed. CSV file is saved at: " + outputFile.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

