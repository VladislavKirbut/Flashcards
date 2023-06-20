package flashcards.project.repository;

import flashcards.project.model.Subtopic;

import java.util.List;

public interface SubtopicRepository {

    List<Subtopic> getSubtopicByTopicId(int topicId);

    void addSubtopicBySubtopicTitle(int topicId, String subtopicTitle);

    void removeSubtopicById(int id);

    boolean existsBySubtopicId(int subtopicId);
}
