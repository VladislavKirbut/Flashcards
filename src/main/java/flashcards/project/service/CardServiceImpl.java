package flashcards.project.service;

import flashcards.project.exception.IncorrectParameters;
import flashcards.project.model.Card;
import flashcards.project.repository.CardRepository;
import flashcards.project.repository.SubtopicRepository;

import java.util.List;

public class CardServiceImpl implements CardService {

    private static final boolean IS_LEARNED = false;
    private final CardRepository repository;
    private final SubtopicRepository subtopicRepository;

    public CardServiceImpl(CardRepository cardRepository, SubtopicRepository subtopicRepository) {
        this.repository = cardRepository;
        this.subtopicRepository = subtopicRepository;
    }

    @Override
    public List<Card> getCards(int subtopicId) {
        validateSubtopicExists(subtopicId);
        return repository.getCardsBySubtopicId(subtopicId);
    }

    @Override
    public void addFlashcard(int subtopicId, String question, String answer) {
        validateSubtopicExists(subtopicId);

        if (!question.isEmpty() && !answer.isEmpty()) {
            repository.addCard(subtopicId, question, answer, IS_LEARNED);
        } else throw new IncorrectParameters("'Question' and 'answer' parameters are invalid.");
    }

    @Override
    public void removeFlashcard(int id) {
        if (!repository.existsById(id))
            throw new IncorrectParameters("Card id not found");

        repository.removeCard(id);
    }

    private void validateSubtopicExists(int subtopicId) {
        if (!subtopicRepository.existsBySubtopicId(subtopicId))
            throw new IncorrectParameters("Subtopic doesn't exist");
    }
}
