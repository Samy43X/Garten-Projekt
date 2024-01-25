package objects;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WeatherIconDaoImpl implements WeatherIconDao {

    private final Jdbi jdbi;

    public WeatherIconDaoImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public void createTable() {
        jdbi.useHandle(handle ->
                handle.execute("CREATE TABLE IF NOT EXISTS weathericon (id INTEGER PRIMARY KEY AUTOINCREMENT, weather_id VARCHAR(255), description VARCHAR(255), weather_code VARCHAR(255), icon BLOB)")
        );
    }
    
    public void dropTable() {
        jdbi.useHandle(handle ->
                handle.execute("DROP TABLE IF EXISTS weathericon;")
        );
    }

    @Override
    public void insertWeatherIcon(@BindBean WeatherIcon weatherIcon) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO weathericon (weather_id, description, weather_code, icon) VALUES (:weatherId, :description, :weatherCode, :icon)")
                        .bindBean(weatherIcon)
                        .execute()
        );
    }
    
    public void insertAllWeatherIcons(List<WeatherIcon> weatherIcons) {
        for (WeatherIcon weatherIcon : weatherIcons) {
            insertWeatherIcon(weatherIcon);
        }
    }


}

