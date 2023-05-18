package flashcards.project.repository;

import flashcards.project.model.Subtopic;
import flashcards.project.model.Topic;

import java.util.List;

public interface TopicRepository {
    List<Topic> getAllTopicList();
    List<Subtopic> getSubtopicByTopicId(int topicId);

    void addTopic(String topicTitle);

    void removeTopic(int id);
}
