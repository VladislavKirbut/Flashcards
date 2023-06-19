package flashcards.project.repository;

import flashcards.project.exception.RepositoryException;
import flashcards.project.model.Subtopic;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubtopicJdbcRepository implements SubtopicRepository {
    private final DataSource db;

    public SubtopicJdbcRepository(DataSource db) {
        this.db = db;
    }
    @Override
    public List<Subtopic> getSubtopicByTopicId(int topicId) {
        String sql = """
                    SELECT subtopic.id                                           AS id,
                           subtopic.topic_id                                     AS topic_id,
                           subtopic.title                                        AS title,
                           count(c.id)                                           AS total_cards_count,
                           count(c.learned) FILTER ( WHERE c.learned = true)     AS learned_cards_count
                    FROM subtopic
                            LEFT JOIN card c ON subtopic.id = c.subtopic_id
                    WHERE topic_id = ?
                    GROUP BY subtopic.id;""";
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, topicId);

            ResultSet resultSet = statement.executeQuery();
            List<Subtopic> subtopicList = new ArrayList<>();
            while (resultSet.next()) {
                subtopicList.add(new Subtopic(
                        resultSet.getInt("id"),
                        resultSet.getInt("topic_id"),
                        resultSet.getString("title"),
                        resultSet.getInt("total_cards_count"),
                        resultSet.getInt("learned_cards_count")
                ));
            }
            return subtopicList;

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    @Override
    public void addSubtopicBySubtopicTitle(int topicId, String subtopicTitle) {
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
    public void removeSubtopicById(int id) {
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

    @Override
    public boolean existsBySubtopicId(int subtopicId) {
        String sql = """
                SELECT TRUE
                FROM subtopic
                WHERE id = ?;
                """;

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
            ) {
            statement.setInt(1, subtopicId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }
}
