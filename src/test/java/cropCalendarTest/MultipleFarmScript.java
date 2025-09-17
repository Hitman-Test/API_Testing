package cropCalendarTest;
//

//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.List;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//public class MultipleFarmScript {
//    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
//    private static final String FARM_DETAIL_URL = "https://micro.satyukt.com/showPolygon?farmID=%s&key=%s";
//    private static final String CROP_CALENDAR_URL = "https://micro.satyukt.com/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=en";
//
//    public static void main(String[] args) throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//
//        // Example farm IDs
//        List<String> farmIds = List.of("124772", "124649", "118436");
//
//        for (String farmId : farmIds) {
//            // Step 1: Call Farm Detail API
//            String farmDetailUrl = String.format(FARM_DETAIL_URL, farmId, API_KEY);
//            HttpRequest farmRequest = HttpRequest.newBuilder()
//                    .uri(URI.create(farmDetailUrl))
//                    .GET()
//                    .build();
//
//            HttpResponse<String> farmResponse = client.send(farmRequest, HttpResponse.BodyHandlers.ofString());
//
//            System.out.println("👉 Farm Detail API URL: " + farmDetailUrl);
//
//            if (farmResponse.statusCode() == 200) {
//                JsonObject farmJson = JsonParser.parseString(farmResponse.body()).getAsJsonObject();
//
//                if (farmJson.has("crop_type") && farmJson.has("crop_stage")) {
//                    String cropType = farmJson.get("crop_type").getAsString();
//                    String sowingDate = farmJson.get("crop_stage").getAsString(); // using crop_stage as sowing_date
//
//                    System.out.println("FarmID: " + farmId);
//                    System.out.println("  Crop Type: " + cropType);
//                    System.out.println("  Sowing Date (from crop_stage): " + sowingDate);
//
//                    // Step 2: Call Crop Calendar API
//                    String cropCalendarUrl = String.format(CROP_CALENDAR_URL, API_KEY, farmId, sowingDate, cropType);
//                    HttpRequest calendarRequest = HttpRequest.newBuilder()
//                            .uri(URI.create(cropCalendarUrl))
//                            .GET()
//                            .build();
//
//                    HttpResponse<String> calendarResponse = client.send(calendarRequest, HttpResponse.BodyHandlers.ofString());
//
//                    System.out.println("👉 Crop Calendar API URL: " + cropCalendarUrl);
//
//                    if (calendarResponse.statusCode() == 200) {
//                        System.out.println("  Crop Calendar Response:");
//                        System.out.println(calendarResponse.body());
//                    } else {
//                        System.out.println("  ❌ Failed to fetch Crop Calendar: " + calendarResponse.statusCode());
//                    }
//                } else {
//                    System.out.println("❌ FarmID " + farmId + " missing crop_type or crop_stage in response");
//                }
//            } else {
//                System.out.println("❌ Failed to fetch farm details for FarmID: " + farmId);
//            }
//
//            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
//          
//            
//        
//        }
//        
//    }
//}

// Data Stored in CSV files 

//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//public class MultipleFarmScript {
//    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
//    private static final String FARM_DETAIL_URL = "https://micro.satyukt.com/showPolygon?farmID=%s&key=%s";
//    private static final String CROP_CALENDAR_URL = "https://micro.satyukt.com/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=en";
//
//    // Change this path as per your system
//    private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData";
//    
//    
//    // Folder Path
//
//    public static void main(String[] args) throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//
//        // Example farm IDs
//        List<String> farmIds = List.of("124656", "124630", "119091", "124740", "124605", "118995");
//
//        // Generate file name with timestamp
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String fileName = OUTPUT_FOLDER + "farm_crop_calendar_" + timestamp + ".csv";
//
//        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
//            // Header row
//            writer.println("farm_id,crop_type,crop_stage,farm_detail_api_url,crop_calendar_api_url,crop_calendar_response,comment");
//
//            for (String farmId : farmIds) {
//                String farmDetailUrl = String.format(FARM_DETAIL_URL, farmId, API_KEY);
//                HttpRequest farmRequest = HttpRequest.newBuilder()
//                        .uri(URI.create(farmDetailUrl))
//                        .GET()
//                        .build();
//
//                HttpResponse<String> farmResponse = client.send(farmRequest, HttpResponse.BodyHandlers.ofString());
//
//                String cropType = "";
//                String cropStage = "";
//                String cropCalendarUrl = "";
//                String comment = "";
//                String calendarData = "";
//
//                if (farmResponse.statusCode() == 200) {
//                    JsonObject farmJson = JsonParser.parseString(farmResponse.body()).getAsJsonObject();
//
//                    if (farmJson.has("crop_type")) {
//                        cropType = farmJson.get("crop_type").getAsString();
//                    } else {
//                        comment += "crop_type missing; ";
//                    }
//
//                    if (farmJson.has("crop_stage")) {
//                        cropStage = farmJson.get("crop_stage").getAsString();
//                    } else {
//                        comment += "crop_stage missing; ";
//                    }
//
//                    // Always call Crop Calendar API
//                    cropCalendarUrl = String.format(
//                            CROP_CALENDAR_URL,
//                            API_KEY,
//                            farmId,
//                            cropStage.isEmpty() ? "" : cropStage,
//                            cropType.isEmpty() ? "" : cropType
//                    );
//
//                    HttpRequest calendarRequest = HttpRequest.newBuilder()
//                            .uri(URI.create(cropCalendarUrl))
//                            .GET()
//                            .build();
//
//                    HttpResponse<String> calendarResponse = client.send(calendarRequest, HttpResponse.BodyHandlers.ofString());
//
//                    calendarData = calendarResponse.statusCode() == 200
//                            ? calendarResponse.body()
//                            : "ERROR " + calendarResponse.statusCode();
//                } else {
//                    comment = "Farm detail API failed";
//                }
//
//                // Clean JSON for CSV (escape quotes)
//                String safeCalendarData = calendarData
//                        .replaceAll("[\\r\\n]+", " ")   // remove newlines
//                        .replace("\"", "\"\"");        // escape quotes for CSV
//
//                // Write ONE ROW per farm_id (wrap JSON with quotes)
//                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
//                        farmId,
//                        cropType,
//                        cropStage,
//                        farmDetailUrl,
//                        cropCalendarUrl,
//                        safeCalendarData,
//                        comment.trim());
//            }
//        }
//
//        System.out.println("✅ CSV file created: " + fileName);
//    }
//}


// Correct code --> passing correct sowing date & Correct csv File date and time format below code 


//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.List;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//public class MultipleFarmScript {
//    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
//    private static final String FARM_DETAIL_URL = "https://micro.satyukt.com/showPolygon?farmID=%s&key=%s";
//    private static final String CROP_CALENDAR_URL =
//            "https://micro.satyukt.com/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=en";
//
//    // Change this path as per your system
//    private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData/";
//
//    public static void main(String[] args) throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//
//        // Example farm IDs
//        List<String> farmIds = List.of("124656", "124630", "119091", "124740", "124605", "118995");
//
//        // Generate file name with human-readable timestamp
//        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
//        String fileName = OUTPUT_FOLDER + "farm_crop_calendar_" + timestamp + ".csv";
//
//        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
//            // Header row
//            writer.println("farm_id,crop_type,sowing_date,days_from_sowing,farm_detail_api_url,crop_calendar_api_url,crop_calendar_response,comment");
//
//            for (String farmId : farmIds) {
//                String farmDetailUrl = String.format(FARM_DETAIL_URL, farmId, API_KEY);
//                HttpRequest farmRequest = HttpRequest.newBuilder()
//                        .uri(URI.create(farmDetailUrl))
//                        .GET()
//                        .build();
//
//                HttpResponse<String> farmResponse = client.send(farmRequest, HttpResponse.BodyHandlers.ofString());
//
//                String cropType = "";
//                String sowingDate = "";
//                String daysFromSowing = "";
//                String cropCalendarUrl = "";
//                String comment = "";
//                String calendarData = "";
//
//                if (farmResponse.statusCode() == 200) {
//                    JsonObject farmJson = JsonParser.parseString(farmResponse.body()).getAsJsonObject();
//
//                    // crop_type
//                    if (farmJson.has("crop_type") && !farmJson.get("crop_type").isJsonNull()) {
//                        cropType = farmJson.get("crop_type").getAsString();
//                    } else {
//                        comment += "crop_type missing; ";
//                    }
//
//                    // Sowing_date
//                    if (farmJson.has("Sowing_date") && !farmJson.get("Sowing_date").isJsonNull()) {
//                        sowingDate = farmJson.get("Sowing_date").getAsString();
//                        try {
//                            LocalDate sowing = LocalDate.parse(sowingDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                            LocalDate today = LocalDate.now();
//                            long diff = ChronoUnit.DAYS.between(sowing, today);
//                            daysFromSowing = String.valueOf(diff);
//                        } catch (Exception e) {
//                            comment += "invalid sowing_date format; ";
//                        }
//                    } else {
//                        comment += "sowing_date missing; ";
//                    }
//
//                    // Always call Crop Calendar API
//                    cropCalendarUrl = String.format(
//                            CROP_CALENDAR_URL,
//                            API_KEY,
//                            farmId,
//                            daysFromSowing.isEmpty() ? "" : daysFromSowing,
//                            cropType.isEmpty() ? "" : cropType
//                    );
//
//                    HttpRequest calendarRequest = HttpRequest.newBuilder()
//                            .uri(URI.create(cropCalendarUrl))
//                            .GET()
//                            .build();
//
//                    HttpResponse<String> calendarResponse = client.send(calendarRequest, HttpResponse.BodyHandlers.ofString());
//
//                    calendarData = calendarResponse.statusCode() == 200
//                            ? calendarResponse.body()
//                            : "ERROR " + calendarResponse.statusCode();
//                } else {
//                    comment = "Farm detail API failed";
//                }
//
//                // Clean JSON for CSV (escape quotes)
//                String safeCalendarData = calendarData
//                        .replaceAll("[\\r\\n]+", " ")   // remove newlines
//                        .replace("\"", "\"\"");        // escape quotes for CSV
//
//                // Write ONE ROW per farm_id (wrap JSON with quotes)
//                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
//                        farmId,
//                        cropType,
//                        sowingDate,
//                        daysFromSowing,
//                        farmDetailUrl,
//                        cropCalendarUrl,
//                        safeCalendarData,
//                        comment.trim());
//            }
//        }
//
//        System.out.println("✅ CSV file created: " + fileName);
//    }
//}
