package flashcards.project.service;

import flashcards.project.model.Card;

import java.util.Optional;

public interface TrainingService {
    Optional<Card> getOneNotLearnedCard(int id, int offset);
    void clickKnow(int cardId);
}
