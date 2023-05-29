package flashcards.project.repository;

import flashcards.project.exception.RepositoryException;
import flashcards.project.model.Card;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardJdbcRepository implements CardRepository {

    private final DataSource db;

    public CardJdbcRepository(DataSource db) {
        this.db = db;
    }
    @Override
    public List<Card> getCardsBySubtopicId(int subtopicId) {
        String sql = """
                SELECT id,
                       subtopic_id,
                       question,
                       answer,
                       learned
                FROM card
                WHERE subtopic_id = ?;
                """;
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, subtopicId);

            ResultSet resultSet = statement.executeQuery();
            List<Card> cardsList = new ArrayList<>();
            while (resultSet.next()) {
                cardsList.add(new Card(
                        resultSet.getInt("id"),
                        resultSet.getInt("subtopic_id"),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        resultSet.getBoolean("learned")
                ));
            }
            return cardsList;

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
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

    @Override
    public Optional<Card> showOneNotLearnedCard(int subtopicId, int offset) {
        String sql = """
                SELECT id,
                       subtopic_id,
                       question,
                       answer,
                       learned
                FROM card
                WHERE subtopic_id = ? AND NOT learned;
                ORDER BY id
                OFFSET ? LIMIT 1;
                """;

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, subtopicId);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Card(
                        resultSet.getInt("id"),
                        resultSet.getInt("subtopic_id"),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        resultSet.getBoolean("learned")
                ));
            } else {
                return Optional.empty();
            }

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }
}
