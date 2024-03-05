package db;

import java.util.Objects;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DatabaseManager {
	private static Jdbi jdbi = null;
	private static String databaseUrl;

    public DatabaseManager(String databaseUrl) {
        // Konfiguriere JDBI mit SQLite-Plugin
    	DatabaseManager.databaseUrl = databaseUrl;
    }

    public void createTableMessungen() {
        jdbi.useHandle(handle -> {
            handle.execute("CREATE TABLE IF NOT EXISTS messungen (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "datum TEXT, " +
                    "zeitangabe TEXT, " +
                    "unixzeit INTEGER, " +
                    "temperatur REAL, " +
                    "feuchtigkeit REAL, " +
                    "bodenfeuchtigkeit REAL, " +
                    "elektrischeLeitfaehigkeit REAL, " +
                    "phWert REAL, " +
                    "lichtintensitaet REAL, " +
                    "co2 REAL" +
                    "pflanzenId INTEGER" +
                    ")");
        });
    }
    
    public void createTablePflanzen() {
        jdbi.useHandle(handle -> {
            handle.execute("CREATE TABLE IF NOT EXISTS pflanzen (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "art TEXT " +
                    ")");
        });
    }
    
    
    
    public void createTableWetterdaten() {
    	jdbi.useHandle(handle -> {
            handle.execute("CREATE TABLE IF NOT EXISTS wetterdaten ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "app_temp DOUBLE,"
                    + "aqi INTEGER,"
                    + "city_name VARCHAR(255),"
                    + "clouds INTEGER,"
                    + "country_code VARCHAR(2),"
                    + "datetime VARCHAR(20),"
                    + "dewpt DOUBLE,"
                    + "dhi DOUBLE,"
                    + "dni DOUBLE,"
                    + "elev_angle DOUBLE,"
                    + "ghi DOUBLE,"
                    + "gust DOUBLE,"
                    + "h_angle DOUBLE,"
                    + "lat DOUBLE,"
                    + "lon DOUBLE,"
                    + "ob_time VARCHAR(20),"
                    + "pod VARCHAR(1),"
                    + "precip DOUBLE,"
                    + "pres DOUBLE,"
                    + "rh INTEGER,"
                    + "slp DOUBLE,"
                    + "snow DOUBLE,"
                    + "solar_rad DOUBLE,"
                    + "state_code VARCHAR(2),"
                    + "station VARCHAR(10),"
                    + "sunrise VARCHAR(5),"
                    + "sunset VARCHAR(5),"
                    + "temp DOUBLE,"
                    + "timezone VARCHAR(50),"
                    + "ts BIGINT,"
                    + "uv DOUBLE,"
                    + "vis DOUBLE,"
                    + "weather_icon VARCHAR(10),"
                    + "weather_description VARCHAR(255),"
                    + "weather_code INTEGER,"
                    + "wind_cdir VARCHAR(1),"
                    + "wind_cdir_full VARCHAR(50),"
                    + "wind_dir INTEGER,"
                    + "wind_spd DOUBLE"
                    + ")");
        });
    }
    
    public void createTableWetterAPI() {
        jdbi.useHandle(handle -> {
            handle.execute("CREATE TABLE IF NOT EXISTS wetterAPI ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "app_temp VARCHAR(255),"
                    + "aqi VARCHAR(255),"
                    + "city_name VARCHAR(255),"
                    + "clouds VARCHAR(255),"
                    + "country_code VARCHAR(255),"
                    + "datetime VARCHAR(255),"
                    + "dewpt VARCHAR(255),"
                    + "dhi VARCHAR(255),"
                    + "dni VARCHAR(255),"
                    + "elev_angle VARCHAR(255),"
                    + "ghi VARCHAR(255),"
                    + "gust VARCHAR(255),"
                    + "h_angle VARCHAR(255),"
                    + "lat VARCHAR(255),"
                    + "lon VARCHAR(255),"
                    + "ob_time VARCHAR(255),"
                    + "pod VARCHAR(255),"
                    + "precip VARCHAR(255),"
                    + "pres VARCHAR(255),"
                    + "rh VARCHAR(255),"
                    + "slp VARCHAR(255),"
                    + "snow VARCHAR(255),"
                    + "solar_rad VARCHAR(255),"
                    + "state_code VARCHAR(255),"
                    + "station VARCHAR(255),"
                    + "sunrise VARCHAR(255),"
                    + "sunset VARCHAR(255),"
                    + "temp VARCHAR(255),"
                    + "timezone VARCHAR(255),"
                    + "ts VARCHAR(255),"
                    + "uv VARCHAR(255),"
                    + "vis VARCHAR(255),"
                    + "icon VARCHAR(255),"
                    + "description VARCHAR(255),"
                    + "code VARCHAR(255),"
                    + "wind_cdir VARCHAR(255),"
                    + "wind_cdir_full VARCHAR(255),"
                    + "wind_dir VARCHAR(255),"
                    + "wind_spd VARCHAR(255)"
                    + ")");
        });
    }
    
    
    
    public void dropTableMessungen() {
    	jdbi.useHandle(handle -> {
            handle.execute("DROP TABLE IF EXISTS messungen");
            System.out.println("Tabelle messungen wurde gelöscht.");
        });
    }
    
    
    public void clearTableMessungen() {
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM messungen");
            handle.createUpdate("DELETE FROM sqlite_sequence WHERE name = 'messungen'")
            .execute();
            System.out.println("Alle Daten aus der Tabelle Messungen gelöscht.");
        });

    }

    public void clearTableWetterdaten() {
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM wetterdaten");
            handle.createUpdate("DELETE FROM sqlite_sequence WHERE name = 'wetterdaten'")
            .execute();
            System.out.println("Alle Daten aus der Tabelle Wetterdaten gelöscht.");
        });

    }
    
 
        

	public static Jdbi getJdbi() {
        if (Objects.nonNull(jdbi)) {
            return jdbi;
        }
        DatabaseManager.jdbi = Jdbi.create(databaseUrl)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin());
		return jdbi;
	} 
}
