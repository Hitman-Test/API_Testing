//package sat2farmAPI;
//
//import org.testng.annotations.Test;
//
//import io.restassured.RestAssured;
//import io.restassured.http.Method;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//public class Crop_Calendar_API {
//	
////	@Test( )
//	//	public void ListOfForm_API()
//	
//	@Test
//	public void Crop_Calendar_API_Test() 
//	{
//		{
//			 // Specify base url
//		      RestAssured.baseURI="https://micro.satyukt.com";
//
//		   
//		   // Request object
//		      RequestSpecification httpRequest = RestAssured.given();
//		      
//		  // Response Object
//		      Response response = httpRequest.request(Method.GET,"/homepage?key=i2z_SxtHkU36Y6sDjDeeZ5VfZ0cw30A9GjJrGSAEupc=");
//		      
//		  
//		 // Print response in console window 
//		      
//		 String responseBody = response.getBody().asString();
//		 System.out.println(" Response body is :"+responseBody );
//		}
//				
//		
//	}
//
//}


package sat2farmAPI;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Crop_Calendar_API {

    @Test
    public void Crop_Calendar_API_Test() {

        // 1. Set base URL
        RestAssured.baseURI = "https://micro.satyukt.com";
        String apiPath = "/homepage?key=i2z_SxtHkU36Y6sDjDeeZ5VfZ0cw30A9GjJrGSAEupc=";
        String methodName = "GET";
        String apiName = "Crop_Calendar_API";

        // 2. Create Request and Capture Start Time
        RequestSpecification httpRequest = RestAssured.given();
        long startTime = System.currentTimeMillis();

        // 3. Send request
        Response response = httpRequest.request(Method.GET, apiPath);

        // 4. Capture End Time
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        // 5. Capture response data
        String responseBody = response.getBody().asString();
        String fullApiUrl = RestAssured.baseURI + apiPath;

        // 6. Get current Date and Time
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 7. Write to Excel
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("API Data");

            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Date and Time");
            header.createCell(1).setCellValue("API Name");
            header.createCell(2).setCellValue("API Link");
            header.createCell(3).setCellValue("Response");
            header.createCell(4).setCellValue("Response Time (ms)");
            header.createCell(5).setCellValue("Method Name");

            // Create data row
            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue(dateTime);
            row.createCell(1).setCellValue(apiName);
            row.createCell(2).setCellValue(fullApiUrl);
            row.createCell(3).setCellValue(responseBody);
            row.createCell(4).setCellValue(responseTime);
            row.createCell(5).setCellValue(methodName);

            // Autosize columns
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save Excel file
            try (FileOutputStream fileOut = new FileOutputStream("CropCalendarAPI_Report.xlsx")) {
                workbook.write(fileOut);
            }

            System.out.println("✅ API response written to Excel successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

