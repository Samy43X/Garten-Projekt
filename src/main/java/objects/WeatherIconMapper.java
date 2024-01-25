package objects;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class WeatherIconMapper implements RowMapper<WeatherIcon> {
	
	public WeatherIcon map(ResultSet rs, StatementContext ctx) throws SQLException {
        WeatherIcon weatherIcon = new WeatherIcon();
        weatherIcon.setWeatherId(rs.getString("weather_id"));
        weatherIcon.setDescription(rs.getString("description"));
        weatherIcon.setWeatherCode(rs.getString("weather_code"));
        weatherIcon.setIcon(rs.getBytes("icon"));
        return weatherIcon;
    }

}
