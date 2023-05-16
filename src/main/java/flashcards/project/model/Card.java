package flashcards.project.model;

public class Card {
    private final int id;
    private final int groupId;
    private final String question;
    private final String answer;
    private final boolean learned;

    public Card(int id, int groupId, String question, String answer, boolean learned) {
        this.id = id;
        this.groupId = groupId;
        this.question = question;
        this.answer = answer;
        this.learned = learned;
    }

    public int getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
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
}
