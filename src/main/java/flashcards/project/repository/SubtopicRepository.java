package flashcards.project.repository;

import flashcards.project.model.Card;
import flashcards.project.model.Subtopic;

import java.util.List;
import java.util.Optional;

public interface SubtopicRepository {

    List<Card> getCardsBySubtopicId(int subtopicId);

    void addSubtopic(int topicId, String subtopicTitle);

    void removeSubtopic(int id);
    Optional<Card> showOneCard(int subtopicId, int offset);
}
