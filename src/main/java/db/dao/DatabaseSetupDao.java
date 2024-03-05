package db.dao;

import org.jdbi.v3.sqlobject.statement.SqlScript;

public interface DatabaseSetupDao {

    @SqlScript("./DATABASE_SCHEMA.sql")
    void createDatabase();

}
