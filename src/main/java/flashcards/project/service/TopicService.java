package flashcards.project.service;

import flashcards.project.model.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> showTopicList();

    void addTheme(String topicTitle);

    void removeTopic(int topicId);
}
