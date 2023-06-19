package flashcards.project.repository;

import flashcards.project.exception.RepositoryException;
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
    public void removeTopicById(int id) {
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
    @Override
    public boolean isExistsByTopicId(int topicId) {
        String sql = """
                SELECT TRUE
                FROM topic
                WHERE id = ?;
                """;

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, topicId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }
}
