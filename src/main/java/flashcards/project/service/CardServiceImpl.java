package flashcards.project.service;

import flashcards.project.exception.IncorrectParameters;
import flashcards.project.model.Card;
import flashcards.project.repository.CardRepository;

import java.util.List;

public class CardServiceImpl implements CardService {

    private final CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Card> getCards(int subtopicId) {
        return repository.getCardsBySubtopicId(subtopicId);
    }

    @Override
    public void addFlashcard(int subtopicId, String question, String answer, boolean learned) {
        if (!question.isEmpty() && !answer.isEmpty()) {
            repository.addCard(subtopicId, question, answer, learned);
        } else throw new IncorrectParameters();
    }

    @Override
    public void removeFlashcard(int id) {
        repository.removeCard(id);
    }
}
