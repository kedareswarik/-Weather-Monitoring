package weatherproject.project1;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MonitoringApp {
    private static final String API_KEY = "29847f4048afad43231673dda2e8acb0"; 
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    public JsonObject getWeatherData(String city) throws IOException {
        String url = String.format(BASE_URL, city, API_KEY);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(new HttpGet(url))) {
             
            
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                return JsonParser.parseString(json).getAsJsonObject();
            } else {
                System.out.println("Error: " + response.getStatusLine().getReasonPhrase());
                return null; 
            }
        }
    }

    public static void main(String[] args) {
        MonitoringApp app = new MonitoringApp();
        String city = "pune"; 
        try {
            
            JsonObject weatherData = app.getWeatherData(city);
            if (weatherData == null) {
                System.out.println("No weather data available.");
                return;
            }

            
            JsonObject mainData = weatherData.getAsJsonObject("main");
            if (mainData == null) {
                System.out.println("Main data not available.");
                return;
            }

            double tempKelvin = mainData.get("temp").getAsDouble();
            double feelsLikeKelvin = mainData.get("feels_like").getAsDouble();
            double tempCelsius = tempKelvin - 273.15; 
            double feelsLikeCelsius = feelsLikeKelvin - 273.15; 

            System.out.println("Weather data for " + city + ":");
            System.out.printf("Coordinates: %.4f, %.4f%n",
                              weatherData.getAsJsonObject("coord").get("lat").getAsDouble(),
                              weatherData.getAsJsonObject("coord").get("lon").getAsDouble());
            System.out.printf("Temperature: %.1f°C%n", tempCelsius);
            System.out.printf("Feels Like: %.1f°C%n", feelsLikeCelsius);
            System.out.printf("Min Temperature: %.1f°C%n", 
                              mainData.get("temp_min").getAsDouble() - 273.15);
            System.out.printf("Max Temperature: %.1f°C%n", 
                              mainData.get("temp_max").getAsDouble() - 273.15);
            System.out.printf("Pressure: %.0f hPa%n", 
                              mainData.get("pressure").getAsDouble());
            System.out.printf("Humidity: %.0f%%%n", 
                              mainData.get("humidity").getAsDouble());
            System.out.printf("Wind Speed: %.2f m/s%n", 
                              weatherData.getAsJsonObject("wind").get("speed").getAsDouble());
            System.out.printf("Wind Direction: %.0f°%n", 
                              weatherData.getAsJsonObject("wind").get("deg").getAsDouble());
            System.out.printf("Cloudiness: %.0f%%%n", 
                              weatherData.getAsJsonObject("clouds").get("all").getAsDouble());
            System.out.printf("Weather Condition: %s%n", 
                              weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
