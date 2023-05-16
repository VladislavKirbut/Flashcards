package flashcards.project.model;

public class Topic {

    private final int id;
    private final String title;

    public Topic(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
