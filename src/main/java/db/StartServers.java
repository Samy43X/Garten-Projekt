package db;


import java.io.IOException;

import org.jdbi.v3.core.Jdbi;

import com.opencsv.CSVReader;

import mqtt.MQTTtoSQLite;
import objects.Data;
import objects.WeatherIconDaoImpl;
import rest.server.ServerStatusController;
import rest.server.StartWeb;
import util.CSVReaderClass;
import util.JsonToWetterdatenConverter;
import util.LocalDateHandler;
import util.WeatherApiClient;

public class StartServers {

	public static void main(String[] args) throws IOException {
		DataCreation creator = new DataCreation();
		
		String databaseUrl = "jdbc:sqlite:garten.db";
		DatabaseManager dbManager = new DatabaseManager(databaseUrl);
		LocalDateHandler formatter = new LocalDateHandler();
		WeatherApiClient weather = new WeatherApiClient();
		JsonToWetterdatenConverter converter = new JsonToWetterdatenConverter();
		WeatherIconDaoImpl dao = new WeatherIconDaoImpl(dbManager.getJdbi());
		CSVReaderClass reader = new CSVReaderClass();
		MQTTtoSQLite mqtt = new MQTTtoSQLite(dbManager.getJdbi(), "tcp://192.168.178.51:1883", "JavaClient");
		

		String date = formatter.getFormattedDate();
		String currentHour = String.valueOf(formatter.getCurrentHour());
		WeatherApiClient weatherApi = new WeatherApiClient();
		
		
        
        //dbManager.deleteWetterAPITable(dbManager.getJdbi());
        //dbManager.createTableMessungen();
        dbManager.createTableWetterdaten();
        dbManager.createTableWetterAPI();
        dbManager.createTablePflanzen();
        dao.dropTable();
        dao.createTable();
        dao.insertAllWeatherIcons(reader.readCSVAndCreateObjects());       
        
        System.out.println("Server started");
        dbManager.dropTableMessungen();
        dbManager.createTableMessungen();
        
        mqtt.startenVonMQTT();
        ServerStatusController status = new ServerStatusController(mqtt);
        StartWeb webServer = new StartWeb();
        webServer.startServer();
        StartWeb.showControlWindow();
        System.out.println("Webserver started");
        
        creator.insertWetterAPIAtFullHour(dbManager.getJdbi(), converter.convertJsonToWetterAPI(weather.getWeatherDataJSON()));
        
        System.out.println(weatherApi.getWeatherDataJSON());
        converter.convertJsonToWetterAPI(weatherApi.getWeatherDataJSON());

        
        
         
	}
	

	
}
