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

//	
//	import java.net.URI;
//	import java.net.http.HttpClient;
//	import java.net.http.HttpRequest;
//	import java.net.http.HttpResponse;
//	import java.time.LocalDate;
//	import java.time.format.DateTimeFormatter;
//	import java.util.List;
//	import java.util.Locale;
//	import java.util.regex.Matcher;
//	import java.util.regex.Pattern;
//
//	public class multipleFarmSameClientScript {
//	    private static final String BASE_URL = "https://micro.satyukt.com/cropadvisory/info";
//	    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
//	    private static final String LANGUAGE = "";
//
//	    // Date format: "03-Apr-2025"
//	    private static final DateTimeFormatter DATE_FORMAT =
//	            DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
//
//	    public static void main(String[] args) throws Exception {
//	        List<String> farmIds = List.of("118483", "124605", "124649");
//
//	        HttpClient client = HttpClient.newHttpClient();
//
//	        for (String farmId : farmIds) {
//	            String url = BASE_URL
//	                    + "?key=" + API_KEY
//	                    + "&farm_id=" + farmId
//	                    + "&language=" + LANGUAGE;
//
//	            HttpRequest request = HttpRequest.newBuilder()
//	                    .uri(URI.create(url))
//	                    .GET()
//	                    .build();
//
//	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//	            System.out.println("Farm ID: " + farmId);
//	            System.out.println("Status: " + response.statusCode());
//	            System.out.println("Response Body: ");
//	            System.out.println(response.body());   // full API response print
//	            System.out.println("------------------------------------");
//
//	            if (response.statusCode() == 200) {
//	                String body = response.body();
//
//	                // regex ने "Date": "03-Apr-2025 to 07-Apr-2025" शोधणे
//	                Pattern pattern = Pattern.compile("\"Date\"\\s*:\\s*\"(\\d{2}-[A-Za-z]{3}-\\d{4}) to (\\d{2}-[A-Za-z]{3}-\\d{4})\"");
//	                Matcher matcher = pattern.matcher(body);
//
//	                if (matcher.find()) {
//	                    String startStr = matcher.group(1);
//	                    String endStr = matcher.group(2);
//
//	                    LocalDate start = LocalDate.parse(startStr, DATE_FORMAT);
//	                    LocalDate end = LocalDate.parse(endStr, DATE_FORMAT);
//	                    LocalDate today = LocalDate.now();
//
//	                    System.out.println("Date Range Found: " + start + " to " + end);
//	                    if (!today.isBefore(start) && !today.isAfter(end)) {
//	                        System.out.println("✅ PASS (today " + today + " is within range)");
//	                    } else {
//	                        System.out.println("❌ FAIL (today " + today + " is outside range)");
//	                    }
//	                } else {
//	                    System.out.println("⚠️ 'Date' field not found in response.");
//	                }
//	            } else {
//	                System.out.println("⚠️ API error, body already shown above.");
//	            }
//
//	            System.out.println("====================================\n");
//	        }
//	    }
//	}





//
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Locale;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//public class multipleFarmSameClientScript {
//
//    private static final String BASE_URL = "https://micro.satyukt.com/homepage_app";
//    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
//
//    // CSV output folder
//    private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData/P&D/Single API Key/"; 
//    public static void main(String[] args) throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//
//        // API URL
//        String url = BASE_URL + "?api_key=" + API_KEY;
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        String sowingDateStr = "";
//        String cropType = "";
//        String cropAge = "";
//        String startDateStr = "";
//        String endDateStr = "";
//        String todayStr = LocalDate.now().toString();
//        String result = "";
//        String body = response.body();
//
//        if (response.statusCode() == 200) {
//            try {
//                JsonObject json = JsonParser.parseString(body).getAsJsonObject();
//
//                // crop type
//                if (json.has("croptype") && !json.get("croptype").isJsonNull()) {
//                    cropType = json.get("croptype").getAsString();
//                } else {
//                    result += "croptype missing; ";
//                }
//
//                // sowing date
//                if (json.has("sowingdate") && !json.get("sowingdate").isJsonNull()) {
//                    sowingDateStr = json.get("sowingdate").getAsString();
//                    LocalDate sowingDate = LocalDate.parse(sowingDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
//
//                    long days = java.time.temporal.ChronoUnit.DAYS.between(sowingDate, LocalDate.now());
//                    cropAge = String.valueOf(days);
//                } else {
//                    result += "sowingdate missing; ";
//                }
//
//            } catch (Exception e) {
//                result = "JSON parse error: " + e.getMessage();
//            }
//        } else {
//            result = "API failed with status " + response.statusCode();
//        }
//
//        // ✅ Ensure folder exists
//        File folder = new File(OUTPUT_FOLDER);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//
//        // dynamic filename
//        String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
//        String outputFile = OUTPUT_FOLDER + "homepage_crop_data_" + timestamp + ".csv";
//
//        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
//            // header
//            writer.println("farm_id,Crop Name,crop age,start_date,end_date,today date,api_url,api_response,result");
//
//            // row (farm_id नाहीये response मध्ये, म्हणून रिकामे ठेवतो)
//            writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
//                    "", // farm_id missing आहे या API मधे
//                    cropType,
//                    cropAge,
//                    startDateStr,
//                    endDateStr,
//                    todayStr,
//                    url,
//                    body.replace("\"", "'"),
//                    result.isEmpty() ? "PASS" : result
//            );
//        }
//
//        System.out.println("✅ CSV file created: " + outputFile);
//    }
//}
//
//
//
//

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.*;

public class multipleFarmSameClientScript {
    private static final String INFO_URL = "https://micro.satyukt.com/cropadvisory/info";
    private static final String HOME_URL = "https://micro.satyukt.com/homepage_app";
    private static final String API_KEY = "oBpsyxOMrVDK_dOc1g2PdZzGSJ-YXR5aBfMDtjSAXp0=";
    private static final String LANGUAGE = "en";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_FORMAT_2 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String OUTPUT_FOLDER = "/home/user/Downloads/Testing/AutmatedScriptTest/TestData/P&D/Single API Key/";

    public static void main(String[] args) throws Exception {
        List<String> farmIds = List.of("125331", "124605", "124649");

        HttpClient client = HttpClient.newHttpClient();

        // ✅ Dynamic file name
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String outputFile = OUTPUT_FOLDER + "farm_check_" + timestamp + ".csv";

        // ✅ Homepage API call (एकदाच)
        HttpRequest homeReq = HttpRequest.newBuilder()
                .uri(URI.create(HOME_URL + "?api_key=" + API_KEY))
                .GET()
                .build();
        HttpResponse<String> homeRes = client.send(homeReq, HttpResponse.BodyHandlers.ofString());
        String bodyHome = homeRes.body();

        // Homepage JSON parse
        JsonObject homeJson = JsonParser.parseString(bodyHome).getAsJsonObject();
        JsonArray homeData = homeJson.getAsJsonArray("data");

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("farm_id,crop_name,crop_age,start_date,end_date,today_date,api_url,api_response,result");

            for (String farmId : farmIds) {
                String cropName = "";
                String cropAge = "";

                // -----------------------
                // 1. homepage_app मधून crop info
                // -----------------------
                if (homeData != null) {
                    for (JsonElement elem : homeData) {
                        JsonObject farm = elem.getAsJsonObject();

                        if (farm.has("farm_id") && !farm.get("farm_id").isJsonNull()) {
                            String fId = farm.get("farm_id").getAsString();
                            if (fId.equals(farmId)) {
                                if (farm.has("croptype") && !farm.get("croptype").isJsonNull()) {
                                    cropName = farm.get("croptype").getAsString();
                                }
                                if (farm.has("sowingdate") && !farm.get("sowingdate").isJsonNull()) {
                                    String sowDate = farm.get("sowingdate").getAsString();
                                    try {
                                        LocalDate sowing = LocalDate.parse(sowDate, DATE_FORMAT_2);
                                        long days = java.time.temporal.ChronoUnit.DAYS.between(sowing, LocalDate.now());
                                        cropAge = String.valueOf(days);
                                    } catch (Exception e) {
                                        cropAge = "";
                                    }
                                }
                            }
                        }
                    }
                }

                // -----------------------
                // 2. cropadvisory/info API मधून date check
                // -----------------------
                String url = INFO_URL
                        + "?key=" + API_KEY
                        + "&farm_id=" + farmId
                        + "&language=" + LANGUAGE;

                HttpRequest infoReq = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> infoRes = client.send(infoReq, HttpResponse.BodyHandlers.ofString());

                String bodyInfo = infoRes.body();
                String startDateStr = "";
                String endDateStr = "";
                String result = "ERROR";
                String todayStr = LocalDate.now().toString();

                if (infoRes.statusCode() == 200) {
                    Pattern pattern = Pattern.compile("\"Date\"\\s*:\\s*\"(\\d{2}-[A-Za-z]{3}-\\d{4}) to (\\d{2}-[A-Za-z]{3}-\\d{4})\"");
                    Matcher matcher = pattern.matcher(bodyInfo);

                    if (matcher.find()) {
                        startDateStr = matcher.group(1);
                        endDateStr = matcher.group(2);

                        LocalDate start = LocalDate.parse(startDateStr, DATE_FORMAT);
                        LocalDate end = LocalDate.parse(endDateStr, DATE_FORMAT);
                        LocalDate today = LocalDate.now();

                        if (!today.isBefore(start) && !today.isAfter(end)) {
                            result = "PASS";
                        } else {
                            result = "FAIL";
                        }
                    } else {
                        result = "NOT_FOUND";
                    }
                }

                // -----------------------
                // 3. CSV row लिहा
                // -----------------------
                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                        farmId,
                        cropName,
                        cropAge,
                        startDateStr,
                        endDateStr,
                        todayStr,
                        url,
                        bodyInfo.replace("\"", "'"),
                        result
                );
            }
        }

        System.out.println("✅ CSV file created: " + outputFile);
    }
}


