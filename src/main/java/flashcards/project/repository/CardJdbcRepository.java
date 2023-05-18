package flashcards.project.repository;

import flashcards.project.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardJdbcRepository implements CardRepository {

    private final DataSource db;

    public CardJdbcRepository(DataSource db) {
        this.db = db;
    }

    @Override
    public void addCard(int subtopicId, String question, String answer, boolean learned) {
        String sql = """
                INSERT INTO card (subtopic_id, question, answer, learned)
                VALUES (?, ?, ?, ?);
                """;

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, subtopicId);
            statement.setString(2, question);
            statement.setString(3, question);
            statement.setBoolean(4, learned);

            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    @Override
    public void removeCard(int cardId) {
        String sql = """
                DELETE FROM card
                WHERE id = ?;
                """;

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, cardId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    @Override
    public void updateCard(int id, boolean learned) {
        String sql = """
                UPDATE card
                SET learned = ?
                WHERE id = ?;
                """;

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setBoolean(1, learned);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

}
