package flashcards.project.repository;

import flashcards.project.model.Card;
import flashcards.project.model.Subtopic;

import java.util.List;

public interface SubtopicRepository {

    List<Card> getCardsBySubtopicId(int subtopicId);

    void addSubtopic(int topicId, String title);

    void removeSubtopic(int id);
}
