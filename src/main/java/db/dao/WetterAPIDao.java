package db.dao;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import objects.Data;

public interface WetterAPIDao extends SqlObject {
    
    @SqlUpdate("DROP TABLE IF EXISTS weatherAPI")
    void dropTable();

    @SqlUpdate(
           """
            UPDATE wetterAPI 
                SET 
                    app_temp = :app_temp, 
                    aqi = :aqi, 
                    city_name = :city_name, 
                    clouds = :clouds,
                    country_code = :country_code, 
                    datetime = :datetime, 
                    dewpt = :dewpt, 
                    dhi = :dhi,
                    dni = :dni, 
                    elev_angle = :elev_angle, 
                    ghi = :ghi, 
                    gust = :gust, 
                    h_angle = :h_angle,
                    lat = :lat, 
                    lon = :lon, 
                    ob_time = :ob_time, 
                    pod = :pod, 
                    precip = :precip, 
                    pres = :pres,
                    rh = :rh, 
                    slp = :slp, 
                    snow = :snow, 
                    solar_rad = :solar_rad, 
                    state_code = :state_code,
                    station = :station, 
                    sunrise = :sunrise, 
                    sunset = :sunset, 
                    temp = :temp, 
                    timezone = :timezone,
                    ts = :ts, 
                    uv = :uv, 
                    vis = :vis, 
                    icon = :icon, 
                    description = :description, 
                    code = :code,
                    wind_cdir = :wind_cdir, 
                    wind_cdir_full = :wind_cdir_full, 
                    wind_dir = :wind_dir, 
                    wind_spd = :wind_spd
                WHERE 
                    some_column = :some_value;
           """)
    void updateWetterAPI(@BindBean Data wetterdaten);

    default void clearTable() {
        final var handle = getHandle();
        handle.begin();
        handle.execute("DELETE FROM wetterAPI");
        handle.execute("DELETE FROM sqlite_sequence WHERE name = 'wetterAPI'");
        handle.commit();
    }

}
