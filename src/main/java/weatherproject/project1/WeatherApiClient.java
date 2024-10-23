package weatherproject.project1;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class WeatherApiClient {
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your actual API key
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    public JsonObject getWeatherData(String city) throws IOException {
        String url = String.format(BASE_URL, city, API_KEY);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(new HttpGet(url))) {
            String json = EntityUtils.toString(response.getEntity());
            return JsonParser.parseString(json).getAsJsonObject();
        }
    }
}
