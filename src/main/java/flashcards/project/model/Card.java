package flashcards.project.model;

public class Card {
    private final int id;
    private final int subtopicId;
    private final String question;
    private final String answer;
    private final boolean learned;

    public Card(int id, int subtopicId, String question, String answer, boolean learned) {
        this.id = id;
        this.subtopicId = subtopicId;
        this.question = question;
        this.answer = answer;
        this.learned = learned;
    }

    public int getId() {
        return id;
    }

    public int getSubtopicId() {
        return subtopicId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isLearned() {
        return learned;
    }

    @Override
    public String toString() {
        return "%-3d %-5d %-10s %-10s %b".formatted(id, subtopicId, question, answer, learned);
    }
}
