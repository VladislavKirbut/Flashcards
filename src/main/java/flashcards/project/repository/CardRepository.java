package flashcards.project.repository;

import flashcards.project.model.Card;

import java.util.Optional;

public interface CardRepository {
    void addCard(int subtopicId, String question, String answer, boolean learned);
    void removeCard(int id);
    void updateCard(int id, boolean learned);
    Optional<Card> showOneNotLearnedCard(int subtopicId, int offset);
}
