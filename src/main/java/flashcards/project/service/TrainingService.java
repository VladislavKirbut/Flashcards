package flashcards.project.service;

import flashcards.project.model.Card;

public interface TrainingService {
    Card getOneNotLearnedCard(int id, int offset);
    Card clickKnow(int id, int offset, boolean learned);
}
