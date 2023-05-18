package flashcards.project.model;

public class Subtopic {
    private final int id;
    private final int topicId;
    private final String title;

    private final int totalCardsCount;
    private final int learnedCardsCount;

    public Subtopic(int id, int topicId, String title, int totalCardsCount, int learnedCardsCount) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.totalCardsCount = totalCardsCount;
        this.learnedCardsCount = learnedCardsCount;
    }

    public int getId() {
        return id;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalCardsCount() {
        return totalCardsCount;
    }

    public int getLearnedCardsCount() {
        return learnedCardsCount;
    }

    @Override
    public String toString() {
        return "%5d %5d %10s %5d / %d".formatted(id, topicId, title, totalCardsCount, learnedCardsCount);
    }
}
