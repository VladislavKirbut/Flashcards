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

public class SubtopicJdbcRepository implements SubtopicRepository {
    private final DataSource db;

    public SubtopicJdbcRepository(DataSource db) {
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
    public void addSubtopic(int topicId, String subtopicTitle) {
        String sql = """
                INSERT INTO subtopic(topic_id, title)
                VALUES (?, ?);
                """;

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, topicId);
            statement.setString(2, subtopicTitle);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    @Override
    public void removeSubtopic(int id) {
        String sql = """
                DELETE FROM subtopic
                WHERE id = ?;
                """;

        try(
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }
}
