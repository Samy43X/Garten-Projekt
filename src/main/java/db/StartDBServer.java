package db;


import java.io.IOException;

import org.jdbi.v3.core.Jdbi;

import com.opencsv.CSVReader;

import objects.Data;
import objects.WeatherIconDaoImpl;
import util.CSVReaderClass;
import util.JsonToWetterdatenConverter;
import util.LocalDateHandler;
import util.WeatherApiClient;

public class StartDBServer {

	public static void main(String[] args) throws IOException {
		DataCreation creator = new DataCreation();
		
		String databaseUrl = "jdbc:sqlite:garten.db";
		DatabaseManager dbManager = new DatabaseManager(databaseUrl);
		LocalDateHandler formatter = new LocalDateHandler();
		WeatherApiClient weather = new WeatherApiClient();
		JsonToWetterdatenConverter converter = new JsonToWetterdatenConverter();
		WeatherIconDaoImpl dao = new WeatherIconDaoImpl(dbManager.getJdbi());
		CSVReaderClass reader = new CSVReaderClass();

		String date = formatter.getFormattedDate();
		Integer currentHour = formatter.getCurrentHour();
		WeatherApiClient weatherApi = new WeatherApiClient();
		
		
        
        //dbManager.deleteWetterAPITable(dbManager.getJdbi());
        dbManager.createTableMessungen();
        dbManager.createTableWetterdaten();
        dbManager.createTableWetterAPI();
        dao.dropTable();
        dao.createTable();
        dao.insertAllWeatherIcons(reader.readCSVAndCreateObjects());
        
        System.out.println("Server started");
        dbManager.clearTableMessungen();
        creator.insertMeasurement(dbManager.getJdbi(), date, currentHour, 25.5, 60.0, 40.0, 800.0, 6.5, 5000.0, 400.0);
        creator.insertWetterAPIbig(dbManager.getJdbi(), converter.convertJsonToWetterAPI(weather.getWeatherDataJSON()));
        
        System.out.println(weatherApi.getWeatherDataJSON());
        converter.convertJsonToWetterAPI(weatherApi.getWeatherDataJSON());

	}
	

}
