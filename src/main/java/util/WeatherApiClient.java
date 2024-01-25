package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApiClient {
	
	/**
	 * 
	 * OPEN WEATHER API
	 *  static final String API_KEY = "9e36d45c062db286656bbc3f568cff7d";
     *	private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=48.158156&lon=11.6294033&units=metric&appid=" + API_KEY;
    */
	
	
	private static final String API_KEY = "89d4b983014549178489954b19266204";
    private static final String API_URL = "https://api.weatherbit.io/v2.0/current?lat=48.158156&lon=11.6294033&key=" + API_KEY + "&lang=de";
    private String link = "https://api.weatherbit.io/v2.0/current?lat=48.158156&lon=11.6294033&key=89d4b983014549178489954b19266204";

    public String getWeatherDataJSON() {
    	String weatherData = "";
        try {
            weatherData = writeWeatherDataJSON(API_URL);
        	//weatherData = ""
        	//		+ "[{"count":1,"data":[{"app_temp":-10.1,"aqi":29,"city_name":"Unterföhring","clouds":82,"country_code":"DE","datetime":"2024-01-21:08","dewpt":-11.1,"dhi":47.4,"dni":432.72,"elev_angle":7.94,"ghi":98.11,"gust":2.57,"h_angle":-45,"lat":48.1582,"lon":11.6294,"ob_time":"2024-01-21 08:40","pod":"d","precip":0,"pres":969,"rh":78,"slp":1033,"snow":0,"solar_rad":58.1,"sources":["analysis","D1024","radar","satellite"],"state_code":"02","station":"D1024","sunrise":"06:53","sunset":"15:55","temp":-8.3,"timezone":"Europe/Berlin","ts":1705826450,"uv":0.55922997,"vis":16,"weather":{"code":803,"icon":"c03d","description":"Aufgelockert Bewölkt"},"wind_cdir":"SSO","wind_cdir_full":"Süd-Südost","wind_dir":150,"wind_spd":1.03}]}"
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    public void printWeatherData(String weatherDataJSON) {
    	System.out.println(weatherDataJSON);
    }
    
    @SuppressWarnings("deprecation")
	private static String writeWeatherDataJSON(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try (InputStream inputStream = connection.getInputStream();
             Scanner scanner = new Scanner(inputStream)) {

            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            connection.disconnect();
        }
    }

}
