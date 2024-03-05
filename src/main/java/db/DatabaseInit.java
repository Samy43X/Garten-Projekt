package db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jdbi.v3.core.Jdbi;

public class DatabaseInit {

    private static final File SCHEMA_PATH = new File("./DATABASE_SCHEMA.sql");

    public static void createDatabase() {
        try {
            final String content = Files.readString(SCHEMA_PATH.toPath());

            final String[] processedContent = content.split(";");

            final Jdbi jdbi = DatabaseManager.getJdbi();

            jdbi.useTransaction(handle -> {
                for (String command : processedContent) {
                    handle.execute(command);
                }
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
