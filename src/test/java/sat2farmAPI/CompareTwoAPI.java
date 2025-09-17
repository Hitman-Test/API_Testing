//package sat2farmAPI;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.testng.annotations.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class CompareTwoAPI {
//
//    // Apps Script WebApp URL (तुझा script publish करून URL इथे टाका)
////    private static final String WEBHOOK_URL = "https://script.google.com/macros/s/AKfycbzcRer8GGeOdpY6PSLyIOVhJ9KuOrl4NNCtoWtn2FIbwqslpmder_nrHuNZ-oyrW2wCwQ/exec";
//    private static final String WEBHOOK_URL ="https://script.google.com/macros/s/AKfycbw7rCcshjPCd1vWuoREzEJn5_HSOgwNEJkOiO-p1_EaOsb1KNeTs82hKuhtB4OdMJ-v/exec";
//    // Old APIs
//    private static final String OLD_HOMEPAGE_API = "https://micro.satyukt.com/homepage_app?api_key=nk6W1mCZnVn7LL2rCRsOYSeZCQRWzd25yXXMxXi34DY=";
//    private static final String OLD_CROP_CALENDAR_API = "https://micro.satyukt.com/display/crop/calender/v2?key=nk6W1mCZnVn7LL2rCRsOYSeZCQRWzd25yXXMxXi34DY=&farm_id=70&sowing_date=13&crop=Pomegranate&lang=en";
//
//    // New APIs
//    private static final String NEW_HOMEPAGE_API = "https://api.sat2farm.com/saytrees_homepage/saytrees_homepage?api_key=nk6W1mCZnVn7LL2rCRsOYSeZCQRWzd25yXXMxXi34DY=";
//    private static final String NEW_CROP_CALENDAR_API = "https://api.sat2farm.com/saytrees_cropcalendar/saytrees_crop_calendar?crop=Pomegranate&lang=en&key=nk6W1mCZnVn7LL2rCRsOYSeZCQRWzd25yXXMxXi34DY=&cropage=13&farm_id=70";
//
//    @Test
//    public void testHomePageAPI() {
//        compareAndLog("HomePage API", OLD_HOMEPAGE_API, NEW_HOMEPAGE_API);
//    }
//
//    @Test
//    public void testCropCalendarAPI() {
//        compareAndLog("Crop Calendar API", OLD_CROP_CALENDAR_API, NEW_CROP_CALENDAR_API);
//    }
//
//    private void compareAndLog(String apiName, String oldApiUrl, String newApiUrl) {
//        // -------- OLD API CALL --------
//        long startOld = System.currentTimeMillis();
//        Response oldResp = RestAssured.given().get(oldApiUrl);
//        long oldTime = System.currentTimeMillis() - startOld;
//
//        String oldApiResponse = oldResp.getBody().asString();
//        int oldStatusCode = oldResp.getStatusCode();
//        String oldMethodType = "GET";
//
//        // -------- NEW API CALL --------
//        long startNew = System.currentTimeMillis();
//        Response newResp = RestAssured.given().get(newApiUrl);
//        long newTime = System.currentTimeMillis() - startNew;
//
//        String newApiResponse = newResp.getBody().asString();
//        int newStatusCode = newResp.getStatusCode();
//        String newMethodType = "GET";
//
//        // -------- SEND TO GOOGLE SHEET --------
//        sendToSheet(apiName,
//                oldApiUrl, oldApiResponse, oldTime, oldMethodType, oldStatusCode,
//                newApiUrl, newApiResponse, newTime, newMethodType, newStatusCode);
//    }
//
//    private void sendToSheet(String apiName,
//                             String oldApiLink, String oldApiResponse, long oldResponseTimeMs, String oldMethodType, int oldStatusCode,
//                             String newApiLink, String newApiResponse, long newResponseTimeMs, String newMethodType, int newStatusCode) {
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("apiName", apiName);
//
//        // Old API
//        data.put("oldApiLink", oldApiLink);
//        data.put("oldApiResponse", truncate(oldApiResponse, 2000));
//        data.put("oldResponseTime", oldResponseTimeMs + " ms");
//        data.put("oldMethodType", oldMethodType);
//        data.put("oldStatusCode", oldStatusCode);
//
//        // New API
//        data.put("newApiLink", newApiLink);
//        data.put("newApiResponse", truncate(newApiResponse, 2000));
//        data.put("newResponseTime", newResponseTimeMs + " ms");
//        data.put("newMethodType", newMethodType);
//        data.put("newStatusCode", newStatusCode);
//
//        // Send to Apps Script WebApp
//        Response resp = RestAssured
//                .given()
//                .header("Content-Type", "application/json")
//                .body(data)
//                .post(WEBHOOK_URL);
//
//        System.out.println("Sheet Webhook Response Code: " + resp.getStatusCode());
//    }
//
//    // मोठा JSON response टाळण्यासाठी truncate function
//    private String truncate(String input, int maxLen) {
//        if (input == null) return "";
//        return input.length() > maxLen ? input.substring(0, maxLen) : input;
//    }
//}

package sat2farmAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CompareTwoAPI {

    // Google Apps Script WebApp URL
    private static final String WEBHOOK_URL = "https://script.google.com/macros/s/AKfycbw7rCcshjPCd1vWuoREzEJn5_HSOgwNEJkOiO-p1_EaOsb1KNeTs82hKuhtB4OdMJ-v/exec";

    // Old APIs
    private static final String OLD_HOMEPAGE_API = "https://micro.satyukt.com/homepage_app?api_key=YOUR_KEY";
    private static final String OLD_CROP_CALENDAR_API = "https://micro.satyukt.com/display/crop/calender/v2?key=YOUR_KEY&farm_id=70&sowing_date=13&crop=Pomegranate&lang=en";

    // New APIs
    private static final String NEW_HOMEPAGE_API = "https://api.sat2farm.com/saytrees_homepage/saytrees_homepage?api_key=YOUR_KEY";
    private static final String NEW_CROP_CALENDAR_API = "https://api.sat2farm.com/saytrees_cropcalendar/saytrees_crop_calendar?crop=Pomegranate&lang=en&key=YOUR_KEY&cropage=13&farm_id=70";

    @Test
    public void testHomePageAPI() {
        compareAndLog("HomePage API", OLD_HOMEPAGE_API, NEW_HOMEPAGE_API);
    }

    @Test
    public void testCropCalendarAPI() {
        compareAndLog("Crop Calendar API", OLD_CROP_CALENDAR_API, NEW_CROP_CALENDAR_API);
    }

    private void compareAndLog(String apiName, String oldApiUrl, String newApiUrl) {
        // -------- OLD API CALL --------
        long startOld = System.currentTimeMillis();
        Response oldResp = RestAssured.given().get(oldApiUrl);
        long oldTime = System.currentTimeMillis() - startOld;

        String oldApiResponse = oldResp.getBody().asString();
        int oldStatusCode = oldResp.getStatusCode();
        String oldMethodType = "GET";

        // -------- NEW API CALL --------
        long startNew = System.currentTimeMillis();
        Response newResp = RestAssured.given().get(newApiUrl);
        long newTime = System.currentTimeMillis() - startNew;

        String newApiResponse = newResp.getBody().asString();
        int newStatusCode = newResp.getStatusCode();
        String newMethodType = "GET";

        // -------- SEND TO GOOGLE SHEET --------
        sendToSheet(apiName, oldApiUrl, oldApiResponse, oldTime, oldMethodType, oldStatusCode,
                newApiUrl, newApiResponse, newTime, newMethodType, newStatusCode);
    }

    private void sendToSheet(String apiName, String oldApiLink, String oldApiResponse, long oldResponseTimeMs,
                             String oldMethodType, int oldStatusCode, String newApiLink, String newApiResponse,
                             long newResponseTimeMs, String newMethodType, int newStatusCode) {

        Map<String, Object> data = new HashMap<>();
        data.put("sheetName", "SayTrees Documentation");
        data.put("apiName", apiName);

        // Old API
        data.put("oldApiLink", oldApiLink);
        data.put("oldApiResponse", truncate(oldApiResponse, 2000));
        data.put("oldResponseTime", oldResponseTimeMs + " ms");
        data.put("oldMethodType", oldMethodType);
        data.put("oldStatusCode", oldStatusCode);

        // New API
        data.put("newApiLink", newApiLink);
        data.put("newApiResponse", truncate(newApiResponse, 2000));
        data.put("newResponseTime", newResponseTimeMs + " ms");
        data.put("newMethodType", newMethodType);
        data.put("newStatusCode", newStatusCode);

        // Pass/Fail Logic
        data.put("passOrFail", oldStatusCode == newStatusCode ? "Pass" : "Fail");

        Response resp = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(data)
                .post(WEBHOOK_URL);

        System.out.println("Sheet Webhook Response Code: " + resp.getStatusCode());
    }

    private String truncate(String input, int maxLen) {
        if (input == null) return "";
        return input.length() > maxLen ? input.substring(0, maxLen) : input;
    }
}
