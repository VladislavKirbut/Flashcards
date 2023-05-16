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

public class TopicJdbcRepository implements TopicRepository {
    private final DataSource db;

    public TopicJdbcRepository(DataSource db) {
        this.db = db;
    }

    // при нажатии в классе topic на тему (открываются подтемы)
    @Override
    public List<Subtopic> getSubtopicByTopicId(int id) {
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
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            List<Subtopic> subtopicList = new ArrayList<>();
            while(resultSet.next()) {
                subtopicList.add(new Subtopic(
                        resultSet.getInt("id"),
                        resultSet.getInt("topic_id"),
                        resultSet.getString("title")
                ));
            }
            return subtopicList;

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    // добавляет в таблицу topic темы
    @Override
    public void addTopic(String title) {
        String sql = """
                INSERT INTO topic (title)
                VALUES (?);
                """;
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, title);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

    // при нажатии удалить набор в таблице subtopic
    @Override
    public void removeTopic(int id) {
        String sql = """
                DELETE * FROM topic
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
