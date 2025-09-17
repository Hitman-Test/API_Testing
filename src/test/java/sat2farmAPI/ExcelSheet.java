package sat2farmAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


// Excel Sheet Link to add the data - https://docs.google.com/spreadsheets/d/1Bi1ykZ9h_7mFvNdETHzcSicfpy5KU8lC9iL8-cK_5-0/edit?gid=0#gid=0

public class ExcelSheet {

    // Constants
    private static final String BASE_URL = "https://micro.satyukt.com";
    private static final String API_KEY = "ruJdGDFVuFi87vXKxw44WStRHf9IMHY2Cx1qR79AJJA=";
    private static final String FARM_ID = "108524";
    private static final String SOWING_DATE = "25";
    private static final String CROP = "Banana";
    private static final String LANG = "en";

    // Replace this with your Apps Script Web App URL
    private static final String WEBHOOK_URL = "https://script.google.com/macros/s/AKfycbz-xr0T9TBEfrsDn2XH6A6FPWlV9XV_GBjdyzDqa_DcdexKOduxeeocQv1KaGDPHWL_kg/exec";

    private RequestSpecification httpRequest;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        httpRequest = RestAssured.given();
    }

    @Test(priority = 0)
    public void testHomePageAPI() {
        String endpoint = "/homepage?key=" + API_KEY;
        long startTime = System.currentTimeMillis();

        Response response = httpRequest.get(endpoint);

        long responseTime = System.currentTimeMillis() - startTime;

        sendToSheet("HomePage API", BASE_URL + endpoint, response.getBody().asString(), responseTime, "testHomePageAPI");
    }

    @Test(priority = 1)
    public void testCropCalendarAPI() {
        String endpoint = String.format(
                "/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=%s",
                API_KEY, FARM_ID, SOWING_DATE, CROP, LANG);

        long startTime = System.currentTimeMillis();
        Response response = httpRequest.get(endpoint);
        long responseTime = System.currentTimeMillis() - startTime;

        sendToSheet("Crop Calendar API", BASE_URL + endpoint, response.getBody().asString(), responseTime, "testCropCalendarAPI");
    }

    private void sendToSheet(String apiName, String apiLink, String apiResponse, long responseTime, String methodName) {
        Map<String, Object> data = new HashMap<>();
        data.put("apiName", apiName);
        data.put("apiLink", apiLink);
        data.put("apiResponse", truncate(apiResponse, 30000)); // To avoid exceeding Google Sheets limit
        data.put("responseTime", responseTime + " ms");
        data.put("methodName", methodName);

//        RestAssured
//            .given()
//            .header("Content-Type", "application/json")
//            .body(data)
//            .post(WEBHOOK_URL)
//            .then()
//            .statusCode(200);
        
        Response resp = RestAssured
        	    .given()
        	    .header("Content-Type", "application/json")
        	    .body(data)
        	    .post(WEBHOOK_URL);

        	System.out.println("Sheet Webhook Response Code: " + resp.getStatusCode());
        
        
    }

    private String truncate(String input, int maxLength) {
        if (input == null) return "";
        return input.length() > maxLength ? input.substring(0, maxLength) + "..." : input;
    }
}
