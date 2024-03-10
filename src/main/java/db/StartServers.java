package db;


import java.io.IOException;

import org.jdbi.v3.core.Jdbi;

import com.google.gson.Gson;
import com.opencsv.CSVReader;

import db.dao.WeatherIconDao;
import mqtt.MQTTManager;
import mqtt.MosquittoLauncher;
import objects.Data;
import objects.WeatherIconDaoImpl;
import rest.server.StartWeb;
import rest.util.ServerStatusController;
import util.CSVReaderClass;
import util.ConfigurationLoader;
import util.TimeManager;
import util.JsonToWetterdatenConverter;
import util.LocalDateHandler;
import util.WeatherApiClient;

public class StartServers {

	public static void main(String[] args) throws IOException {

		
		String databaseUrl = "jdbc:sqlite:garten.db";
		DatabaseManager dbManager = new DatabaseManager(databaseUrl);
        DatabaseInit.createDatabase();
        final var jdbi = DatabaseManager.getJdbi();
		LocalDateHandler formatter = new LocalDateHandler();
		WeatherApiClient weather = new WeatherApiClient();
		JsonToWetterdatenConverter converter = new JsonToWetterdatenConverter();
		WeatherIconDaoImpl dao = new WeatherIconDaoImpl(DatabaseManager.getJdbi());
		CSVReaderClass reader = new CSVReaderClass();
		MosquittoLauncher broker = new MosquittoLauncher(ConfigurationLoader.loadApplicationProperties().getProperty("mosquitto.exe.path"), ConfigurationLoader.loadApplicationProperties().getProperty("mosquitto.conf.path"));
		MQTTManager mqttManager = MQTTManager.getInstance();

		

		String date = formatter.getFormattedDate();
		String currentHour = String.valueOf(formatter.getCurrentHour());
		WeatherApiClient weatherApi = new WeatherApiClient();

        //final var weatherIconDao = jdbi.onDemand(WeatherIconDao.class);
        //weatherIconDao.insertAllWeatherIcons(reader.readCSVAndCreateObjects());
        
        System.out.println("Server started");
        //dbManager.dropTableMessungen();
        //dbManager.createTableMessungen();
        
        mqttManager.connect("tcp://localhost:1883", "JavaClient");
        StartWeb webServer = new StartWeb();
        webServer.startServer();
        StartWeb.showControlWindow();
        System.out.println("Webserver started");
        
        TimeManager.insertWetterAPIAtFullHour(DatabaseManager.getJdbi(), converter.convertJsonToWetterAPI(weather.getWeatherDataJSON()));
        
        System.out.println(weatherApi.getWeatherDataJSON());
        converter.convertJsonToWetterAPI(weatherApi.getWeatherDataJSON());
        System.out.println(new Gson().toJson(MQTTManager.getInstance().isConnected()) + " | COLD");

        
        
         
	}
	

	
}
