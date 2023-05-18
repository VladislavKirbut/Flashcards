package flashcards.project.repository;

public interface CardRepository {
    void addCard(int subtopicId, String question, String answer, boolean learned);
    void removeCard(int id);
    void updateCard(int id, boolean learned);
}
