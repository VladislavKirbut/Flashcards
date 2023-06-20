package flashcards.project.service;

import flashcards.project.controller.SubtopicPage;
import flashcards.project.controller.TrainingFirstCard;
import flashcards.project.controller.TrainingPage;
import flashcards.project.exception.BusinessException;
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
    public Optional<Card> getFirst(int subtopicId) {
        return cardRepository.showOneNotLearnedCard(subtopicId,0);
    }

    @Override
    public Optional<Card> getOneNotLearnedCard(int subtopicId, int previousCard) {
        validateSubtopicExists(subtopicId);
        return cardRepository.showOneNotLearnedCard(subtopicId, previousCard);
    }

  @Override
    public void clickKnow(int cardId) {
        boolean exists = cardRepository.updateCard(cardId, LEARNED);
        if (!exists)
            throw new BusinessException("Card is missing");
    }

    private void validateSubtopicExists(int subtopicId) {
        if (!subtopicRepository.existsBySubtopicId(subtopicId))
            throw new IncorrectParameters("Subtopic id not found");
    }
}
