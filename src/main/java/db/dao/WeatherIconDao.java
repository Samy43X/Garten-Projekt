package db.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import objects.WeatherIcon;

public interface WeatherIconDao {

    @SqlUpdate("INSERT INTO weathericon (weather_id, description, weather_code, icon) VALUES (:weatherId, :description, :weatherCode, :icon)")
    void insertWeatherIcon(@BindBean WeatherIcon weatherIcon);

    @SqlBatch("INSERT INTO weathericon (weather_id, description, weather_code, icon) VALUES (:weatherId, :description, :weatherCode, :icon)")
    void insertAllWeatherIcons(@BindBean List<WeatherIcon> weatherIcons);

    @SqlUpdate("DROP TABLE IF EXISTS weathericon")
    void dropWeatherIconTable();
}
