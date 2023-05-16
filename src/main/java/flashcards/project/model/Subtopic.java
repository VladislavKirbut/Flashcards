package flashcards.project.model;

public class Subtopic {
    private final int id;
    private final int topicId;
    private final String title;

    public Subtopic(int id, int topicId, String title) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
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
}
