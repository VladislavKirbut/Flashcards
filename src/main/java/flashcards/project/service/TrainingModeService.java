package flashcards.project.service;

import flashcards.project.model.Card;
import flashcards.project.repository.CardRepository;

public class TrainingModeService implements TrainingService {

    private final CardRepository repository;

    public TrainingModeService(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card getOneNotLearnedCard(int id, int offset) {
        if (repository.showOneNotLearnedCard(id, offset).isPresent()) {
            return repository.showOneNotLearnedCard(id, offset).get();
        } else throw new RuntimeException();
    }

    @Override
    public Card clickKnow(int id, int offset, boolean learned) {
        repository.updateCard(id, learned);
        return getOneNotLearnedCard(id, offset);
    }
}
