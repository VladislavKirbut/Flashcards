package flashcards.project.service;

import flashcards.project.model.Card;

import java.util.List;

public interface CardService {
    List<Card> getCards(int subtopicId);
    void addFlashcard(int subtopicId, String question, String answer, boolean learned);
    void removeFlashcard(int id);
}
