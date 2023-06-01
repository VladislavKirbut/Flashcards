package flashcards.project.service;

import flashcards.project.model.Subtopic;

import java.util.List;

public interface SubtopicService {
    List<Subtopic> getSubtopicList(int topicId);
    void addSubtopic(int topicId, String subtopicTitle);
    void removeSubtopic(int id);
}
