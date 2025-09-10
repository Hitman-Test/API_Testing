package sat2farmAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
public class All_API_Data {
    // Constants
    private static final String BASE_URL = "https://micro.satyukt.com";
    private static final String API_KEY = "ruJdGDFVuFi87vXKxw44WStRHf9IMHY2Cx1qR79AJJA=";
    private static final String FARM_ID = "108524";
    private static final String SOWING_DATE = "25";
    private static final String CROP = "Banana";
    private static final String LANG = "en";
    
    
    // Google Apps Script Webhook URL --> Web App URl --> this link Getting to the Sheet
//    private static final String WEBHOOK_URL = "https://script.google.com/macros/s/AKfycbw7rCcshjPCd1vWuoREzEJn5_HSOgwNEJkOiO-p1_EaOsb1KNeTs82hKuhtB4OdMJ-v/exec";
    private static final String WEBHOOK_URL ="https://script.google.com/macros/s/AKfycbw7rCcshjPCd1vWuoREzEJn5_HSOgwNEJkOiO-p1_EaOsb1KNeTs82hKuhtB4OdMJ-v/exec";
    // Sheet Link -> add Data -->  https://docs.google.com/spreadsheets/d/1U8wvhCA--urLbk6xJK7enP-VE5duXUFC_MEyddo72jk/edit?gid=0#gid=0
    
    private RequestSpecification httpRequest;
 
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        httpRequest = RestAssured.given();
      System.out.println( "Test");
    }
    
    
    // Test Start
    
    @Test(priority = 0)
    public void testHomePageAPI() {
        String endpoint = "/homepage?key=" + API_KEY;
        long startTime = System.currentTimeMillis();
        Response response = httpRequest.get(endpoint);
        long responseTime = System.currentTimeMillis() - startTime;
        sendToSheet("HomePage API", BASE_URL + endpoint, response.getBody().asString(),
                responseTime, "GET", response.getStatusCode());
    }
    @Test(priority = 1)
    public void testCropCalendarAPI() {
        String endpoint = String.format(
                "/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=%s",
                API_KEY, FARM_ID, SOWING_DATE, CROP, LANG);
        long startTime = System.currentTimeMillis();
        Response response = httpRequest.get(endpoint);
        long responseTime = System.currentTimeMillis() - startTime;
        sendToSheet("Crop Calendar API", BASE_URL + endpoint, response.getBody().asString(),
                responseTime, "GET", response.getStatusCode());
    }
    
    @Test(priority=2)
 
    
    
    private void sendToSheet(String apiName, String apiLink, String apiResponse,
                             long responseTimeMs, String methodType, int statusCode) {
        // ms -> s मध्ये convert करतो
        double responseTimeSec = responseTimeMs / 1000.0;
        Map<String, Object> data = new HashMap<>();
        data.put("apiName", apiName);
        data.put("apiLink", apiLink);
        data.put("apiResponse", truncate(apiResponse, 30000));
        data.put("responseTime", String.format("%.2f s", responseTimeSec));
        data.put("methodType", methodType);
        data.put("statusCode", statusCode);
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


//package sat2farmAPI;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//import java.util.HashMap;
//import java.util.Map;
//
//public class All_API_Data {
//	// Constants
//	private static final String BASE_URL = "https://micro.satyukt.com";
//	private static final String API_KEY = "ruJdGDFVuFi87vXKxw44WStRHf9IMHY2Cx1qR79AJJA=";
//	private static final String FARM_ID = "108524";
//	private static final String SOWING_DATE = "25";
//	private static final String CROP = "Banana";
//	private static final String LANG = "en";
//
//	// Google Apps Script Webhook URL --> Web App URl --> this link Getting to the
//	// Sheet
////    private static final String WEBHOOK_URL = "https://script.google.com/macros/s/AKfycbw7rCcshjPCd1vWuoREzEJn5_HSOgwNEJkOiO-p1_EaOsb1KNeTs82hKuhtB4OdMJ-v/exec";
//	private static final String WEBHOOK_URL = "https://script.google.com/macros/s/AKfycbw7rCcshjPCd1vWuoREzEJn5_HSOgwNEJkOiO-p1_EaOsb1KNeTs82hKuhtB4OdMJ-v/exec";
//	// Sheet Link -> add Data -->
//	// https://docs.google.com/spreadsheets/d/1U8wvhCA--urLbk6xJK7enP-VE5duXUFC_MEyddo72jk/edit?gid=0#gid=0
//
//	private RequestSpecification httpRequest;
//
//	@BeforeClass
//	public void setUp() {
//		RestAssured.baseURI = BASE_URL;
//		httpRequest = RestAssured.given();
//		System.out.println("Test");
//	}
//
//	@Test(priority = 0)
//	public void testHomePageAPI() {
//		String endpoint = "/homepage?key=" + API_KEY;
//		long startTime = System.currentTimeMillis();
//		Response response = httpRequest.get(endpoint);
//		long responseTime = System.currentTimeMillis() - startTime;
//		sendToSheet("HomePage API", BASE_URL + endpoint, response.getBody().asString(), responseTime, "GET",
//				response.getStatusCode());
//	}
//
//	@Test(priority = 1)
//	public void testCropCalendarAPI() {
//		String endpoint = String.format("/display/crop/calender/v2?key=%s&farm_id=%s&sowing_date=%s&crop=%s&lang=%s",
//				API_KEY, FARM_ID, SOWING_DATE, CROP, LANG);
//		long startTime = System.currentTimeMillis();
//		Response response = httpRequest.get(endpoint);
//		long responseTime = System.currentTimeMillis() - startTime;
//		sendToSheet("Crop Calendar API", BASE_URL + endpoint, response.getBody().asString(), responseTime, "GET",
//				response.getStatusCode());
//	}
//
//	private void sendToSheet(String apiName, String apiLink, String apiResponse, long responseTimeMs, String methodType,
//			int statusCode) {
//		double responseTimeSec = responseTimeMs / 1000.0;
//		Map<String, Object> data = new HashMap<>();
//		data.put("sheetName", "All APIs"); // 🔹 Target Sheet
//		data.put("apiName", apiName);
//		data.put("apiLink", apiLink);
//		data.put("apiResponse", truncate(apiResponse, 30000));
//		data.put("responseTime", String.format("%.2f s", responseTimeSec));
//		data.put("methodType", methodType);
//		data.put("statusCode", statusCode);
//
//		Response resp = RestAssured.given().header("Content-Type", "application/json").body(data).post(WEBHOOK_URL);
//		System.out.println("Sheet Webhook Response Code: " + resp.getStatusCode());
//	}
//
//	private String truncate(String input, int maxLength) {
//		if (input == null)
//			return "";
//		return input.length() > maxLength ? input.substring(0, maxLength) + "..." : input;
//	}
//}
