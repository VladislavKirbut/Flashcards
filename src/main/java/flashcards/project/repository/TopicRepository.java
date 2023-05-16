package flashcards.project.repository;

import flashcards.project.model.Subtopic;

import java.util.List;

public interface TopicRepository {
    List<Subtopic> getSubtopicByTopicId(int id);

    void addTopic(String title);

    void removeTopic(int id);
}
