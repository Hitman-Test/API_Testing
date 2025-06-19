//package sat2farmAPI;
//
//import org.testng.annotations.Test;
//
//import io.restassured.RestAssured;
//import io.restassured.http.Method;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//public class Test_API {
//	
//	
//	 // Declare API key as a constant (or you can load from config file)
//	
//	private static final String BASE_URL = "https://micro.satyukt.com";
//    private static final String API_KEY = "ruJdGDFVuFi87vXKxw44WStRHf9IMHY2Cx1qR79AJJA=";
//    private static final String FARM_ID = "108524";
//    private static final String SOWING_DATE = "25";
//    private static final String CROP = "Banana";
//    private static final String LANG = "en";
//    private static final String LANGUAGE = "en";
//
//    @Test(priority=0)
//    public void HomePage_API() {
//        
//        // Specify base URL
//        RestAssured.baseURI = BASE_URL;
//
//        // Request object
//        RequestSpecification httpRequest = RestAssured.given();
//        
//        // Response Object
//        String endpoint = "/homepage?key=" + API_KEY;
//        Response response = httpRequest.request(Method.GET, endpoint);
//
//        // Print response in console window
//        String responseBody = response.getBody().asString();
//        System.out.println("Response body is: " + responseBody);
//    }
//    
//    
//    @Test(priority=1)
//    public void Crop_Calendar_API() {
//        
//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification httpRequest = RestAssured.given();
//
//        // Properly formatted endpoint with all query params
//        String endpoint = String.format(
//            "/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=%s",
//            API_KEY, FARM_ID, SOWING_DATE, CROP, LANG
//        );
//
//        Response response = httpRequest.request(Method.GET, endpoint);
//
//        String responseBody = response.getBody().asString();
//        System.out.println("Response Body is: " + responseBody);
//
//        int statusCode = response.getStatusCode();
//        System.out.println("Status Code: " + statusCode);
//    }
//    
//
//}

// Remove Duplicate Code OR Modify 

package sat2farmAPI;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test_API {

    // Constants
    private static final String BASE_URL = "https://micro.satyukt.com";
    private static final String API_KEY = "ruJdGDFVuFi87vXKxw44WStRHf9IMHY2Cx1qR79AJJA=";
    private static final String FARM_ID = "108524";
    private static final String SOWING_DATE = "25";
    private static final String CROP = "Banana";
    private static final String LANG = "en";

    private RequestSpecification httpRequest;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        httpRequest = RestAssured.given();
    }

    @Test(priority = 0)
    public void testHomePageAPI() {
        String endpoint = "/homepage?key=" + API_KEY;

        Response response = httpRequest.request(Method.GET, endpoint);

        printResponse("HomePage API", response);
    }

    @Test(priority = 1)
    public void testCropCalendarAPI() {
        String endpoint = String.format(
            "/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=%s",
            API_KEY, FARM_ID, SOWING_DATE, CROP, LANG
        );

        Response response = httpRequest.request(Method.GET, endpoint);

        printResponse("Crop Calendar API", response);
    }

    private void printResponse(String apiName, Response response) {
        System.out.println("===== " + apiName + " =====");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("=============================\n");
    }
}



