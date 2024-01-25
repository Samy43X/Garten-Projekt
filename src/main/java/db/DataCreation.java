package db;


import java.util.Map;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import objects.Data;

public class DataCreation {
	
	public void insertMeasurement(Jdbi jdbi, String datum, int stundenangabe, double temperatur, double feuchtigkeit, 
            double bodenfeuchtigkeit, double elektrischeLeitfaehigkeit, 
            double phWert, double lichtintensitaet, double co2) {
		
		try (Handle handle = jdbi.open()) {
			handle.createUpdate("INSERT INTO messungen (datum, stundenangabe, temperatur, feuchtigkeit, " +
			         "bodenfeuchtigkeit, elektrischeLeitfaehigkeit, phWert, lichtintensitaet, co2) " +
			         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
			.bind(0, datum)
			.bind(1, stundenangabe)
			.bind(2, temperatur)
			.bind(3, feuchtigkeit)
			.bind(4, bodenfeuchtigkeit)
			.bind(5, elektrischeLeitfaehigkeit)
			.bind(6, phWert)
			.bind(7, lichtintensitaet)
			.bind(8, co2)
			.execute();
			};
	    } 
	
	public void insertWeatherdata(Jdbi jdbi, Data wetterdaten) {
	    jdbi.useHandle(handle -> {
	        handle.createUpdate("INSERT INTO wetterdaten (app_temp, aqi, city_name, clouds, country_code, datetime, dewpt, dhi, dni, elev_angle, ghi, gust, h_angle, lat, lon, ob_time, pod, precip, pres, rh, slp, snow, solar_rad, state_code, station, sunrise, sunset, temp, timezone, ts, uv, vis, weather_icon, weather_description, weather_code, wind_cdir, wind_cdir_full, wind_dir, wind_spd) "
	                + "VALUES (:app_temp, :aqi, :city_name, :clouds, :country_code, :datetime, :dewpt, :dhi, :dni, :elev_angle, :ghi, :gust, :h_angle, :lat, :lon, :ob_time, :pod, :precip, :pres, :rh, :slp, :snow, :solar_rad, :state_code, :station, :sunrise, :sunset, :temp, :timezone, :ts, :uv, :vis, :weather_icon, :weather_description, :weather_code, :wind_cdir, :wind_cdir_full, :wind_dir, :wind_spd)")
	                .bindBean(wetterdaten)
	                .execute();
	    });
	}

	
	public void insertWetterAPI(Jdbi jdbi, Data wetterdaten) {
	    jdbi.useHandle(handle -> {
	        handle.createUpdate("INSERT INTO wetterAPI ("
	                + "app_temp, aqi, city_name, clouds, country_code, datetime, dewpt, dhi, dni, elev_angle, "
	                + "ghi, gust, h_angle, lat, lon, ob_time, pod, precip, pres, rh, slp, snow, solar_rad, "
	                + "state_code, station, sunrise, sunset, temp, timezone, ts, uv, vis, icon, "
	                + "description, code, wind_cdir, wind_cdir_full, wind_dir, wind_spd) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
	                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")		
	            .bind("app_temp", wetterdaten.getApp_temp())
	            .bind("aqi", wetterdaten.getAqi())
	            .bind("city_name", wetterdaten.getCity_name())
	            .bind("clouds", wetterdaten.getClouds())
	            .bind("country_code", wetterdaten.getCountry_code())  // Änderung hier
	            .bind("datetime", wetterdaten.getDatetime())
	            .bind("dewpt", wetterdaten.getDewpt())
	            .bind("dhi", wetterdaten.getDhi())
	            .bind("dni", wetterdaten.getDni())
	            .bind("elev_angle", wetterdaten.getElev_angle())
	            .bind("ghi", wetterdaten.getGhi())
	            .bind("gust", wetterdaten.getGust())
	            .bind("h_angle", wetterdaten.getH_angle())
	            .bind("lat", wetterdaten.getLat())
	            .bind("lon", wetterdaten.getLon())
	            .bind("ob_time", wetterdaten.getOb_time())
	            .bind("pod", wetterdaten.getPod())
	            .bind("precip", wetterdaten.getPrecip())
	            .bind("pres", wetterdaten.getPres())
	            .bind("rh", wetterdaten.getRh())
	            .bind("slp", wetterdaten.getSlp())
	            .bind("snow", wetterdaten.getSnow())
	            .bind("solar_rad", wetterdaten.getSolar_rad())
	            .bind("state_code", wetterdaten.getState_code())
	            .bind("station", wetterdaten.getStation())
	            .bind("sunrise", wetterdaten.getSunrise())
	            .bind("sunset", wetterdaten.getSunset())
	            .bind("temp", wetterdaten.getTemp())
	            .bind("timezone", wetterdaten.getTimezone())
	            .bind("ts", wetterdaten.getTs())
	            .bind("uv", wetterdaten.getUv())
	            .bind("vis", wetterdaten.getVis())
	            .bind("icon", wetterdaten.getIcon())
	            .bind("description", wetterdaten.getDescription())
	            .bind("code", wetterdaten.getCode())
	            .bind("wind_cdir", wetterdaten.getWind_cdir())
	            .bind("wind_cdir_full", wetterdaten.getWind_cdir_full())
	            .bind("wind_dir", wetterdaten.getWind_dir())
	            .bind("wind_spd", wetterdaten.getWind_spd())
	            .execute();
	    });
	}

	public void insertWetterAPIsmol(Jdbi jdbi, Data wetterdaten) {
	    jdbi.useHandle(handle -> {
	        handle.createUpdate("INSERT INTO wetterAPI ("
	                + "app_temp) "
	                + "VALUES (?)")		
	            .bind("app_temp", wetterdaten.getApp_temp())
	            
	      
	            .execute();
	    });}
	
	public void insertWetterAPIbig(Jdbi jdbi, Data wetterdaten) {
	    jdbi.useHandle(handle -> {
	        handle.createUpdate("INSERT INTO wetterAPI ("
	                + "app_temp, aqi, city_name, clouds, country_code, datetime, dewpt, dhi, dni, elev_angle, "
	                + "ghi, gust, h_angle, lat, lon, ob_time, pod, precip, pres, rh, slp, snow, solar_rad, "
	                + "state_code, station, sunrise, sunset, temp, timezone, ts, uv, vis, icon, "
	                + "description, code, wind_cdir, wind_cdir_full, wind_dir, wind_spd) "
	                + "VALUES (:app_temp, :aqi, :city_name, :clouds, :country_code, :datetime, :dewpt, :dhi, :dni, :elev_angle, "
	                + ":ghi, :gust, :h_angle, :lat, :lon, :ob_time, :pod, :precip, :pres, :rh, :slp, :snow, :solar_rad, "
	                + ":state_code, :station, :sunrise, :sunset, :temp, :timezone, :ts, :uv, :vis, :icon, "
	                + ":description, :code, :wind_cdir, :wind_cdir_full, :wind_dir, :wind_spd)")
	            .bindBean(wetterdaten)
	            .execute();
	    });
	
}
	}

