package flashcards.project.repository;

import flashcards.project.model.Card;
import java.util.List;

public interface SubtopicRepository {

    List<Card> getCardsBySubtopicId(int subtopicId);

    void addSubtopic(int topicId, String subtopicTitle);

    void removeSubtopic(int id);
}
