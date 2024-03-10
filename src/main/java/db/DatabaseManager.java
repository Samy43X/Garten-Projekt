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
