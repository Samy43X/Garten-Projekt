package db.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface MessungenDao {
	
	@SqlUpdate("DELETE FROM messungen")
    void deleteAllMessungen();

    @SqlUpdate("DELETE FROM sqlite_sequence WHERE name = 'messungen'")
    void resetMessungenSequence();

    default void clearTableMessungen() {	
        deleteAllMessungen();
        resetMessungenSequence();
    }
    
    

}
