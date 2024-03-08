package db.dao;


import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface PflanzenDao {
	
	@SqlUpdate("INSERT INTO pflanzen (art) VALUES (:art)")
    void insertPflanzen(@Bind("art") String art);
	

}
