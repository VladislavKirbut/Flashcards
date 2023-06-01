package flashcards.project.service;

import flashcards.project.exception.IncorrectParameters;
import flashcards.project.model.Card;
import flashcards.project.repository.CardRepository;
import flashcards.project.repository.SubtopicRepository;

import java.util.Optional;

public class TrainingModeService implements TrainingService {

    private static final boolean LEARNED = true;
    private final CardRepository cardRepository;
    private final SubtopicRepository subtopicRepository;

    public TrainingModeService(CardRepository cardRepository, SubtopicRepository subtopicRepository) {
        this.cardRepository = cardRepository;
        this.subtopicRepository = subtopicRepository;
    }

    @Override
    public Optional<Card> getOneNotLearnedCard(int subtopicId, int offset) {
        validateSubtopicExists(subtopicId);

        return cardRepository.showOneNotLearnedCard(subtopicId, offset);
    }

  @Override
    public Optional<Card> clickKnow(int cardId, int offset) {
        cardRepository.updateCard(cardId, LEARNED);
        return getOneNotLearnedCard(cardId, offset);
    }

    private void validateSubtopicExists(int subtopicId) {
        if (subtopicRepository.existsBySubtopicId(subtopicId))
            throw new IncorrectParameters("Subtopic id not found");
    }
}
