package db.dao;

import db.DatabaseManager;
import lombok.Getter;

@Getter
public abstract class DaoManager {
	
	public DaoManager() {

        final var jdbi = DatabaseManager.getJdbi();
        
		messungenDao = jdbi.onDemand(MessungenDao.class);
		weatherIconDao = jdbi.onDemand(WeatherIconDao.class);
		wetterAPIDao = jdbi.onDemand(WetterAPIDao.class); 
		pflanzenDao = jdbi.onDemand(PflanzenDao.class);
		
	}
		
	protected static MessungenDao messungenDao;
	protected static WeatherIconDao weatherIconDao;
	protected static WetterAPIDao wetterAPIDao;
	protected static PflanzenDao pflanzenDao;
	
	public static MessungenDao getMessungenDao() {
		return messungenDao;
	}

	public static WeatherIconDao getWeatherIconDao() {
		return weatherIconDao;
	}

	public static WetterAPIDao getWetterAPIDao() {
		return wetterAPIDao;
	}

	public static PflanzenDao getPflanzenDao() {
		return pflanzenDao;
	}



	
	
}
