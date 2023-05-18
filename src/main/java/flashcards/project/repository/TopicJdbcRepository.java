package flashcards.project.repository;

import flashcards.project.exception.RepositoryException;
import flashcards.project.model.Subtopic;
import flashcards.project.model.Topic;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicJdbcRepository implements TopicRepository {
    private final DataSource db;

    public TopicJdbcRepository(DataSource db) {
        this.db = db;
    }

    @Override
    public List<Topic> getAllTopicList() {
        String sql = """
                SELECT id,
                       title
                FROM topic;
                """;

        try (
                Connection connection = db.getConnection();
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Topic> topicList = new ArrayList<>();
            while (resultSet.next()) {
                topicList.add(new Topic(
                        resultSet.getInt("id"),
                        resultSet.getString("title")
                ));
            }
            return topicList;
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
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
            while(resultSet.next()) {
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
    public void addTopic(String topicTitle) {
        String sql = """
                INSERT INTO topic (title)
                VALUES (?);
                """;
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, topicTitle);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    @Override
    public void removeTopic(int id) {
        String sql = """
                DELETE FROM topic
                WHERE id = ?;
                """;
        try (
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
