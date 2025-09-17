package desktopSHeet;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Incolnmple data 


public class TestData {
    private static final String ODS_FILE = "/home/user/Documents/Testing/DesktopTest.ods"; // तुमचा path द्या
    private static final String API_URL = "https://micro.satyukt.com/weather/data/daypart?key=9iQmP5MpxekitLp7O04PmTjLEHIo5pJW7rMEAC6EIzM=&farm_id=97905";

    public static void main(String[] args) throws Exception {
        // 1) Call API
        String method = "GET";
        long start = System.currentTimeMillis();
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);

        int status = conn.getResponseCode();
        long responseTimeMs = System.currentTimeMillis() - start;

        String responseBody;
        try (Scanner s = new Scanner(
                status >= 200 && status < 400 ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8.name())) {
            s.useDelimiter("\\A");
            responseBody = s.hasNext() ? s.next() : "";
        }
        conn.disconnect();

     

      
    }
}
