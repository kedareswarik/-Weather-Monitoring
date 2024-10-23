package weatherproject.project1;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherScheduler {
    private final WeatherApiClient weatherApiClient = new WeatherApiClient();

    public void start(String string) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
			    try {
			        // Fetch weather data for each city
			        JsonObject delhiWeather = weatherApiClient.getWeatherData("Delhi");

			        // Process and display the weather data
			        System.out.println("Weather data for Delhi: " + delhiWeather);

			        // Extract temperature data
			        double tempKelvin = delhiWeather.getAsJsonObject("main").get("temp").getAsDouble();
			        double tempCelsius = tempKelvin - 273.15; // Convert to Celsius
			        System.out.println("Current temperature in Delhi: " + tempCelsius + "°C");

			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
		}, 0, 5, TimeUnit.MINUTES); // Runs every 5 minutes
    }
}
