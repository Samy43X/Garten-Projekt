package objects;

import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface WeatherIconDao {
    
    @SqlUpdate("CREATE TABLE IF NOT EXISTS weathericon (id INT PRIMARY KEY AUTOINCREMENT, weather_id VARCHAR(255), description VARCHAR(255), weather_code INT, icon BLOB)")
    void createTable();

    @SqlUpdate("INSERT INTO weathericon (weather_id, description, weather_code, icon) VALUES (:weatherId, :description, :weatherCode, :icon)")
    void insertWeatherIcon(WeatherIcon weatherIcon);


}

