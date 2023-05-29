package flashcards.project.repository;

import flashcards.project.model.Subtopic;
import flashcards.project.model.Topic;

import java.util.List;

public interface TopicRepository {
    List<Topic> getAllTopicList();
    void addTopic(String topicTitle);

    void removeTopicById(int id);
}
