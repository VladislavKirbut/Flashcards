package flashcards.project;

import org.postgresql.ds.PGSimpleDataSource;

public class FlashcardsApp {
    public static void main(String[] args) {
        PGSimpleDataSource database = new PGSimpleDataSource();
        database.setUser(System.getenv("POSTGRES_DB_USER"));
        database.setPassword(System.getenv("POSTGRES_DB_PASSWORD"));
        database.setURL(System.getenv("POSTGRES_DB_URL"));
    }
}
